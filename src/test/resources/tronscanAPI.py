
import re
import os
import requests
import json

def ding(fail_class):
    mes = ""
    for i in fail_class:
        mes += "- " + i + "\n"
    webhook = "https://oapi.dingtalk.com/robot/send?access_token=dc0c335fd70aebb29bda7ad63b936283fbd9545d6a04b8aadc85f28a106c7aff"
    header = {"Content-Type": "application/json","Charset": "UTF-8"}
    tex = "test"
    message ={
        "actionCard":{
        "title": "Tronscan API自动化用例执行结果",
        "hideAvatar": "0",
        "btnOrientation": "0",
        "text":"Tronscan API自动化执行失败的用例:\n" + mes,
        "btns": [
            {
                "title": "点击查看测试报告",
                "actionURL": "http://172.16.22.178:8080/job/Tronscan_AutoTest/HTML_20Report/"
            }
        ]
        },
        "msgtype": "actionCard"
    }
    message_json = json.dumps(message)
    info = requests.post(url=webhook,data=message_json,headers=header)
    print(info.text)

def dingSucess():
    webhook = "https://oapi.dingtalk.com/robot/send?access_token=dc0c335fd70aebb29bda7ad63b936283fbd9545d6a04b8aadc85f28a106c7aff"
    header = {"Content-Type": "application/json","Charset": "UTF-8"}
    tex = "test"
    message ={
        "actionCard":{
        "title": "Tronscan API自动化用例执行结果",
        "hideAvatar": "0",
        "btnOrientation": "0",
        "text":"Tronscan API自动化执行通过，无失败用例\n",
        "btns": [
            {
                "title": "点击查看测试报告",
                "actionURL": "http://172.16.22.178:8080/job/Tronscan_Api/HTML_20Report/"
            }
        ]
        },
        "msgtype": "actionCard"
    }
    message_json = json.dumps(message)
    info = requests.post(url=webhook,data=message_json,headers=header)
    print(info.text)


def file_name(file_dir):
    for root,dirs,files in os.walk(file_dir):
        return files

filesname = file_name("/Users/tron/.jenkins/workspace/Tronscan_Api/build/reports/tests/tronscanAPI/classes/")
count=0
failClass = []
fail_class = []
for file in filesname:
    dir = "/Users/tron/.jenkins/workspace/Tronscan_Api/build/reports/tests/tronscanAPI/classes/" + file
    print(dir)
    fp = open(dir,"r")
    for s in fp.readlines():
        li=re.findall("infoBox failures", s)
        if len(li)>0:
            failClass.append(file)
            count=count+len(li)
print("search:",count,">>>infoBox failures")
if count > 0:
    fail_class = []
    for failclass in failClass:
        fail_class.append(failclass.split(".")[3])
        print(fail_class)
    ding(fail_class)
fp.close()


