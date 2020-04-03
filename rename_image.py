import os
import sys


def add_prefix_subfolders():
    # mark = 'test_'
    old_names = os.listdir(path)
    for old_name in old_names:
        if old_name != sys.argv[0]:
            # os.rename(os.path.join(path, old_name), os.path.join(path, mark+old_name))
            # print(old_name, "has been renamed successfully! New name is: ", mark+old_name)
            new_name = old_name.replace('test_', 'ti_')
            os.rename(os.path.join(path, old_name), os.path.join(path, new_name))
            print(old_name, "has been renamed successfully! New name is: ", new_name)


if __name__ == '__main__':
    path = r'D:\02ability\Mymou\Behavioural Testing Unit (Android)\Mymou\app\src\main\res\drawable\transitive_inference_picture'
    add_prefix_subfolders()
