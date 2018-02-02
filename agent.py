import sys
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler
import logging, shutil, time, os
import logging.handlers
import hashlib, getpass
import json, socket
import upload


os.system("title agent")

def create_zip(json_path):
    pass


def prerun(timestamp):
    """
    Creation of local data folders
    Returns folder path in timestamp
    :param timestamp:
    :return: time_stamped_folder
    """
    if not os.path.isdir('log'):
        os.makedirs('log')
    if not os.path.isdir('collected'):
        os.makedirs('collected')
    if not os.path.isdir('done'):
        os.makedirs('done')
    time_stamped_folder = os.path.join('collected', timestamp)
    if not os.path.isdir(time_stamped_folder):
        os.makedirs(time_stamped_folder)
    return time_stamped_folder


def setlogger():
    """

    :return:
    """
    LOGGER = logging.getLogger('WDAgent')
    LOGGER.setLevel(logging.DEBUG)
    formatter = logging.Formatter('%(asctime)s - %(message)s')
    handler = logging.handlers.RotatingFileHandler(os.path.join('log', 'watchdog.log'),
                                                   maxBytes=40000, backupCount=5)
    handler.setFormatter(formatter)
    LOGGER.addHandler(handler)
    return LOGGER


def display(filecount=0):
    os.system("cls")
    if filecount > 0:
        print "\n".join(["Execution in progress ... (Ctr+C to stop)",
                         "Files Dropped [" + str(filecount) + "]"])
    else:
        print "\n".join(["Execution in progress ...",
                         "Files Dropped [" + str(filecount) + "]"])


class GlobalSettings(object):
    TIMESTAMP = str(int(time.time()))
    FILE_EXTENTIONS = ['.dll', '.exe', '.pyc', '.py', '.jar', '.h', '.c']
    FILE_COLLECTED = []
    AGENT_PATH = os.path.dirname(os.path.abspath(__file__)).lower()


class WDError(Exception):
    pass


class MetaDataCollector(object):
    def __init__(self):
        self.metadata_list = None
        self.file_info_list = []
        self.gs = GlobalSettings()

    def _hash_dump(self, filename, block_size=65536):
        sha256 = hashlib.sha256()
        if os.path.isfile(filename):
            with open(filename, 'rb') as f:
                for block in iter(lambda: f.read(block_size), b''):
                    sha256.update(block)
            return sha256.hexdigest()

    def _dump_json(self, timestamp):
        host_name = str(socket.gethostname())
        with open(os.path.join(self.gs.AGENT_PATH, 'done', 'C' + '.json'), 'w') as JSON:
            json.dump(self.file_info_list, JSON)

    def _populate_json(self):
        for file in self.metadata_list:
            file_sha256 = self._hash_dump(file['timestamped_file'])
            file_info = {"system_file_path": file["file_path"],
                         "system_file_name": file["filename"],
                         "system_file_hash": file_sha256,
                         "system_user_name": getpass.getuser()}
            self.file_info_list.append(file_info)

    def run(self, timestamp):
        self._populate_json()
        self._dump_json(timestamp)


class MyHandler(FileSystemEventHandler):
    def __init__(self):
        self.settings = GlobalSettings()
        self.time_stamped_folder = prerun(self.settings.TIMESTAMP)
        self.logger = setlogger()
        self.file_collected = []

    def set_completeflag(self):
        print "Adding <TIMESTAMPED>.flg"
        flag_path = "{TIMESTAMPED}.flg".format(TIMESTAMPED=self.settings.TIMESTAMP)
        with open(os.path.join('done', flag_path), "wb") as COMPLETE:
            COMPLETE.write(self.settings.TIMESTAMP)

    def process(self, event):
        file_in_event = os.path.basename(event.src_path)
        if event.is_directory is False and event.src_path.find(self.settings.AGENT_PATH) == -1:
            if os.path.splitext(file_in_event)[1].lower() in self.settings.FILE_EXTENTIONS:
                try:
                    file_in_event_timestamped = os.path.join(self.time_stamped_folder, file_in_event)
                    try:
                        shutil.copy(event.src_path, file_in_event_timestamped)
                    except Exception, ioerr:
                        self.logger.error(ioerr.message)
                    finally:
                        self.logger.debug(str(event.event_type).lower() + " -> " + event.src_path.lower())
                        self.settings.FILE_COLLECTED.append(event.src_path)
                        count = len(os.listdir(self.time_stamped_folder))
                        self.file_collected.append({"file_path": event.src_path,
                                                    "timestamped_file": file_in_event_timestamped,
                                                    "filename": file_in_event})
                        display(count)
                except WDError:
                    return

    def on_created(self, event):
        self.process(event)

    def on_modified(self, event):
        self.process(event)


def main():
     try:
        if len(sys.argv) > 1:
            display()
            args = sys.argv[1:]
        else:
            raise WDError("Provide valid drive to monitor")
        observer = Observer()
        myhandler = MyHandler()
        metadata = MetaDataCollector()
        uploader = upload.Upload()
        observer.schedule(myhandler, path=args[0].lower() if args else '.', recursive=True)
        observer.start()

        try:
            while True:
                time.sleep(1)
        except KeyboardInterrupt:
            observer.stop()
        finally:
            observer.join()
            if len(myhandler.file_collected):
                metadata.metadata_list = myhandler.file_collected
                print "Generating JSON ... please wait"
                metadata.run(myhandler.settings.TIMESTAMP)
                myhandler.set_completeflag()
                print "Uploading collected data ..."
                uploader.start()
            else:
                raise WDError("No data collected !")
     except WDError, err:
         print err.message


if __name__ == '__main__':
    main()
