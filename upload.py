import boto3
import os
from boto.s3.connection import S3Connection


s3 = boto3.client('s3')

S3 = boto3.resource('s3')


class Upload(object):
    def __init__(self):
        self._bucket_name = 'wannacode'
        self._flag_directory = 'collected'
        self._local_flag_directory = 'done'
        self._report_directory = 'VT_Output/resources'

    def _list_flag_files(self):
        flag_json_files = sorted(os.listdir(self._local_flag_directory))
        return flag_json_files

    def _upload_file(self, file_names):
        for file_name in file_names:
            s3.upload_file(os.path.join(self._local_flag_directory, file_name),
                           self._bucket_name, '{}/{}'.format(self._flag_directory, file_name))

    def start(self, process_type='upload'):
        if process_type == 'upload':
            flag_json_list = self._list_flag_files()
            self._upload_file(flag_json_list)
        if process_type == 'download':
            conn = S3Connection()
            # assumes boto.cfg setup
            bucket = conn.get_bucket(self._bucket_name)
            for obj in bucket.get_all_keys(prefix=self._report_directory):
                try:
                    if os.path.splitext(os.path.basename(obj.key))[1] == '.svg':
                        S3.Bucket(self._bucket_name).download_file(obj.key, os.path.basename(obj.key))
                except:
                    continue
#
# U = Upload()
# U.start(process_type='download')

# ls = s3.list_buckets()
# bucket = s3.get_bucket(bucket_name)
#
# for b in ls:
#     # print ls[b]
#     bucket_list = bucket.list(prefix='collected')
#     print bucket_list


# conn = boto.connect_s3('AKIAJFIUUNXWZYEMJR3A', '4YjYS4THGBZZqVEj69KNhg1fzCEmZJgRVfmAkxZm')
#
# bucket = conn.get_bucket(bucket_name)
#
# bl = bucket.list(prefix='collected')
#
# print bl.__dict__
#
#
# from boto.s3.connection import S3Connection
#
# conn = S3Connection() # assumes boto.cfg setup
# bucket = conn.get_bucket(bucket_name)
# for obj in bucket.get_all_keys():
#     print obj.key

# s3.put_object(Bucket=bucket_name, Key='collected/1517302097/')

# s3.upload_file(filename, bucket_name, 'collected/1517302112_7zG.exe')