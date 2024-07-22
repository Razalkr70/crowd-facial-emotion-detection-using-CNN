import datetime
import json

import numpy
from django.contrib import auth
from django.contrib.auth.decorators import login_required
from django.http import HttpResponse, JsonResponse
from django.shortcuts import render

# Create your views here.
from CFED.models import *


def login(request):
    return render(request,'loginindex.html')

def logout (request) :
    auth.logout(request)
    return render(request,"loginindex.html")

def logincode(request):
    uname=request.POST['textfield']
    pswrd = request.POST['textfield2']
    ob = login_table.objects.get(username=uname, password=pswrd)
    if ob.type== "admin" :
        ob1=auth.authenticate(username='admin',password='admin')
        if ob1 is not None :
            auth.login(request,ob1)

        return HttpResponse("<Script> alert('success');window.location='Admin_home'</Script>")
    else:
        return HttpResponse("<Script> alert('invalid username or password');window.location='login'</Script>")




@login_required(login_url='/')
def Admin_home(request):
    return render(request,'adminindex.html')
@login_required(login_url='/')



def camera_info(request):
    ob = camera_table.objects.all()
    return render(request,'camera info.html',{'data':ob})
@login_required(login_url='/')


def ser_cam(request):
    cm=request.POST['textfield']
    ob = camera_table.objects.filter(USER__firstname__istartswith=cm)
    return render(request, 'camera info.html', {'data': ob,"cm":cm})
@login_required(login_url='/')


def complaint(request):
    ob = complaint_table.objects.all()
    return render(request,'complaint.html',{'data':ob})
@login_required(login_url='/')

def ser_comp(request):
    cp = request.POST['textfield']
    ob = complaint_table.objects.filter(date=cp)
    return render(request, 'complaint.html', {'data': ob, "cp": cp})
@login_required(login_url='/')


def reply(request,id):
    request.session['coid']=id
    return render(request,'reply.html')
@login_required(login_url='/')

def reply_code(request):
    rep=request.POST['textfield']
    ob=complaint_table.objects.get(id=request.session['coid'])
    ob.reply=rep
    ob.save()
    return HttpResponse("<Script> alert('Replied');window.location='complaint'</Script>")
@login_required(login_url='/')


def View_feedback(request):
    ob = feedback_table.objects.all()
    return render(request,'view feedback.html',{'data':ob})
@login_required(login_url='/')

def ser_feed(request):
    fb = request.POST['textfield']
    ob = feedback_table.objects.filter(date=fb)
    return render(request, 'view feedback.html', {'data': ob, "fb": fb})
@login_required(login_url='/')


def View_User(request):
    ob=user_table.objects.all()
    return render(request,'view user.html',{'data':ob})
@login_required(login_url='/')

def ser_user(request):
    nm=request.POST['textfield']
    ob = user_table.objects.filter(firstname__istartswith=nm)
    return render(request, 'view user.html', {'data': ob,"nm":nm})

#++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=


def login_code(request):
    print("%77777777777777777777777")
    un = request.POST['uname']
    pw = request.POST['pswrd']
    print("%%%%%%%%%%%%%%%%%%%%%%%", request.POST)

    users = login_table.objects.get(username = un, password = pw,type = 'user')
    if users is None:
        data = {"task" : "invalid"}
    else:
        data = {"task" : "valid","lid":users.id}
    l = json.dumps(data)
    return HttpResponse(l)


def register(request):
    fname = request.POST['fname']
    lname = request.POST['lname']
    place = request.POST['place']
    post = request.POST['post']
    pin = request.POST['pin']
    phone = request.POST['phone']
    email = request.POST['email']
    un = request.POST['un']
    pw = request.POST['pw']
    obb=login_table()
    obb.username=un
    obb.password=pw
    obb.type="user"
    obb.save()

    ob = user_table()
    ob.firstname=fname
    ob.lastname=lname
    ob.place = place
    ob.post = post
    ob.pin = pin
    ob.LOGIN=obb
    ob.phone = phone
    ob.email = email
    ob.uname = un
    ob.pwd = pw

    ob.save()
    data = {"task": "valid"}
    l = json.dumps(data)
    print(l)
    return HttpResponse(l)

def vcomplaints(request):
    complaint = request.POST['complaint']
    usr = request.POST['lid']
    cmplnt = complaint_table()

    cmplnt.complaint = complaint
    cmplnt.date =datetime.datetime.today()
    cmplnt.reply = 'Pending'
    cmplnt.USER = user_table.objects.get(LOGIN=usr)
    cmplnt.save()
    data = {"task": "valid"}
    l = json.dumps(data)
    print(l)
    return HttpResponse(l)


def v_reply1(request):
    usr = request.POST['lid']
    ob = complaint_table.objects.filter(USER__LOGIN__id=usr)
    data = []
    for i in ob:
        replyy = {"complaint":i.complaint,"rply":i.reply, "dt":str(i.date)}
        data.append(replyy)
    l = json.dumps(data)
    return HttpResponse(l)



def searchcomplaintvieww(request):
    usr = request.POST['lid']
    dates = request.POST['date']
    print(dates,"yyyyy")
    ob = complaint_table.objects.filter(USER__LOGIN__id=usr,date=dates)
    data = []
    for i in ob:
        replyy = {"complaint":i.complaint,"rply":i.reply, "dt":str(i.date)}
        data.append(replyy)
    l = json.dumps(data)
    return HttpResponse(l)


def v_notification(request):
    ob = notification_table.objects.all()
    data = []
    for i in ob:
        notific = {"noti":i.notification, "dt":str(i.date)}
        data.append(notific)
    print(data,"UUUUUUUUUUUUUUUUUUUUUU")
    l = json.dumps(data)
    return HttpResponse(l)

def vcamera(request) :
    ob = camera_table.objects.all()
    data = []
    for i in ob:
        camr = {"camera":i.camNo, "dt":str(i.date)}
        data.append(camr)
    print(data, "UUUUUUUUUUUUUUUUUUUUUU")
    l = json.dumps(data)
    return HttpResponse(l)



def deletecamera(request):
    cid = request.POST['cid']
    print(cid,"lllllllllllllllllllll")
    cmplnt = camera_table.objects.get(id=cid)
    cmplnt.delete()
    data = {"task": "valid"}
    l = json.dumps(data)
    print(l)
    return HttpResponse(l)



def viewcamera(request) :
    ob = camera_table.objects.all()
    data = []
    for i in ob:
        camr = {"camera":i.camNo,"details":i.details,"id":i.id}
        data.append(camr)
    print(data, "UUUUUUUUUUUUUUUUUUUUUU")
    l = json.dumps(data)
    return HttpResponse(l)






def videosrc(request) :
    ob = videoDetails_table.objects.all()
    data = []
    for i in ob:
        Video = {"vidlink":str(i.frame.url), "dt":str(i.date)}
        data.append(Video)
    print(data, "UUUUUUUUUUUUUUUUUUUUUU")
    l = json.dumps(data)
    return HttpResponse(l)



def videosrc_search(request) :
    date=request.POST['date']
    ob = videoDetails_table.objects.filter(date=date)
    data = []
    for i in ob:
        Video = {"vidlink":str(i.frame.url), "dt":str(i.date)}
        data.append(Video)
    print(data, "UUUUUUUUUUUUUUUUUUUUUU")
    l = json.dumps(data)
    return HttpResponse(l)
def viewemotion(request) :
    eid=request.POST['eid']
    emotions=('angry','disgust','fear','happy','sad','surprise','neutral','DROWSINESS DETECTED')
    countlist=[0,0,0,0,0,0,0,0]
    ob = videoDetails_table.objects.filter(date__date=datetime.datetime.today(),date__hour=datetime.datetime.now().hour,CAMERA__id=eid)
    print(ob)
    if (len(ob)==0):
        return JsonResponse({'task':'na'})
    for i in ob:
        print(i.emotion,"=============================")
        print(i.emotion,"=============================")
        ind=emotions.index(i.emotion)
        countlist[ind]+=1
    t=numpy.argmax(countlist)
    print(emotions[t],"===================================")
    return JsonResponse({"task":emotions[t]})


# def videosrc_search(request) :
#     date=request.POST['date']
#     ob = video_table.objects.filter(date_time__icontains=date)
#     data = []
#     for i in ob:
#         Video = {"vidlink":str(i.video.url), "dt":str(i.date_time)}
#         data.append(Video)
#     print(data, "UUUUUUUUUUUUUUUUUUUUUU")
#     l = json.dumps(data)
#     return HttpResponse(l)


def acamera(request) :
    camno = request.POST['camno']
    dts = request.POST['dts']
    lid = request.POST['lid']


    ob = camera_table()
    ob.camNo = camno
    ob.details = dts
    ob.USER = user_table.objects.get(LOGIN=lid)
    ob.save()

    data = {"task": "valid"}
    l = json.dumps(data)
    print(l)
    return HttpResponse(l)


def afeedback(request) :
    fdbk = request.POST['fdbk']
    lid = request.POST['lid']

    ob = feedback_table()
    ob.feedback = fdbk
    ob.date=datetime.datetime.today()
    ob.USER = user_table.objects.get(LOGIN=lid)
    ob.save()

    data = {"task": "valid"}
    l = json.dumps(data)
    print(l)
    return HttpResponse(l)

def uservprofile(request) :
    lid = request.POST['lid']

    ob = user_table.objects.filter(LOGIN=lid)

    if len(ob)>0:
        xx=camera_table.objects.filter(USER=ob[0].id)
        print(ob[0].firstname)

        data={"task":"valid","fname":ob[0].firstname,"lname":ob[0].lastname,"email":ob[0].email,"count":str(len(xx))}

    else:
        data={"task":"invalid"}

    r=json.dumps(data)
    return HttpResponse(r)


def insertEmotions(request):
    # sid = request.GET["staffid"]
    emot = request.GET["emo"]
    camid = request.GET["camid"]

    ob=videoDetails_table()
    ob.CAMERA_id=camid
    ob.emotion=emot
    ob.frame=""
    ob.date=datetime.datetime.today()
    ob.save()

    return HttpResponse("ok")




#
# def notificationv(request) :
#
#
#
#     ob = notification_table.objects.filter()
#     data = []
#     for i in ob:
#         notification = {"cameraid":i.CAMERA_id, "date":str(i.date),"time":str(i.time),"emotion":i.emotion,"image":i.image.url}
#         data.append(notification)
#     print(data, "UUUUUUUUUUUUUUUUUUUUUU")
#     l = json.dumps(data)
#     return HttpResponse(l)


def insertEmotions(request):
    # sid = request.GET["staffid"]
    emot = request.GET["emo"]
    camid = request.GET["camid"]
    img = request.GET["img"]
    print(img,"kkkkkkkkkk")
    ob=videoDetails_table()
    ob.CAMERA_id=camid
    ob.emotion=emot
    ob.frame=img
    ob.date=datetime.datetime.today()
    ob.time=datetime.datetime.now()
    ob.save()

    return HttpResponse("ok")


from django.http import HttpResponse
from django.db.models import Avg
from .models import notification_table
import json


def notificationv(request):
    # Get notifications within the last 5 minutes
    import datetime
    five_minutes_ago = datetime.datetime.now() - datetime.timedelta(minutes=5)
    ob = videoDetails_table.objects.filter(time__gte=five_minutes_ago)
    if len(ob)==0:
        data = []
        for i in ob:

            notification = {
                "cameraid": "null",
                "date": "null",
                "time": "null",
                "emotion": "null",
                "image": "null"
            }
            data.append(notification)
        print(data, "UUUUUUUUUUUUUUUUUUUUUU")
        l = json.dumps(data)
        return HttpResponse(l)
    else:
        data = []
        total_emotion = 0
        num_notifications = 0

        for i in ob:
            notification = {
                "cameraid": i.CAMERA.camNo,
                "date": str(i.date),
                "time": str(i.time),
                "emotion": i.emotion,
                "image":i.frame.url
            }
            data.append(notification)
        print(data, "UUUUUUUUUUUUUUUUUUUUUU")
        l = json.dumps(data)
        return HttpResponse(l)

        # Calculate total emotion for average calculation
    #     total_emotion += int(i.emotion)
    #     num_notifications += 1
    #
    # # Calculate average emotion
    # average_emotion = total_emotion / num_notifications if num_notifications > 0 else 0
    #
    # # Print notifications data for debugging
    # print(data, "UUUUUUUUUUUUUUUUUUUUUU")
    #
    # # Construct JSON response
    # response_data = {
    #     "notifications": data,
    #     "average_emotion": average_emotion
    # }
    # json_response = json.dumps(response_data)
    #
    # return HttpResponse(json_response, content_type="application/json")
    #     print(data, "UUUUUUUUUUUUUUUUUUUUUU")
    #     l = json.dumps(data)
    #     return HttpResponse(l)


# def avgimag(request):
#     # Get notifications within the last 5 minutes
#     import datetime
#     import json
#     from django.http import HttpResponse
#     five_minutes_ago = datetime.datetime.now() - datetime.timedelta(minutes=5)
#     ob = videoDetails_table.objects.filter(time__gte=five_minutes_ago)
#
#     if len(ob) == 0:
#         data = []
#         average_emotion = 0  # If no notifications, average emotion is 0
#     else:
#         data = []
#         total_emotion = 0
#         num_notifications = 0
#
#         for i in ob:
#             try:
#                 total_emotion += int(i.emotion)
#                 num_notifications += 1
#             except ValueError:
#                 # Handle cases where emotion is not convertible to int
#                 pass
#
#             notification = {
#                 "cameraid": i.CAMERA.camNo,
#                 "date": str(i.date),
#                 "time": str(i.time),
#                 "emotion": i.emotion,
#                 "image": i.frame.url
#             }
#             data.append(notification)
#
#         # Calculate average emotion
#         average_emotion = total_emotion / num_notifications if num_notifications > 0 else 0
#         if average_emotion==0:
#             avg='angry'
#         elif average_emotion==1:
#                avg= 'disgust'
#         elif average_emotion==2:
#                avg='fear'
#         elif average_emotion == 3:
#             avg='happy'
#         elif average_emotion == 4:
#            avg=' sad'
#         elif average_emotion==5:
#              avg='surprise'
#         else:
#              avg='neutral'
#
#
#     print(data, "UUUUUUUUUUUUUUUUUUUUUU")
#     response_data = {
#         "notifications": len(data),
#         "average_emotion": avg,
#         "camera": 1
#     }
#     response_list=[response_data]
#     print(response_data,"jjjjjjjjjjjjjjjjjjjjjjjjjjjjj")
#     json_response = json.dumps(response_list)
#     return HttpResponse(json_response, content_type="application/json")
#

def avgimag(request):
    import datetime
    import json
    from django.http import HttpResponse

    five_minutes_ago = datetime.datetime.now() - datetime.timedelta(minutes=5)
    ob = videoDetails_table.objects.filter(time__gte=five_minutes_ago)

    if len(ob) == 0:
        data = []
        average_emotion = "neutral"  # Default if no notifications
    else:
        data = []
        emotion_count = {
            "angry": 0,
            "disgust": 0,
            "fear": 0,
            "happy": 0,
            "sad": 0,
            "surprise": 0,
            "neutral": 0
        }

        for i in ob:
            # Assuming 'emotion' field contains string emotions like "angry", "happy", etc.
            emotion_count[i.emotion] += 1

            notification = {
                "cameraid": i.CAMERA.camNo,
                "date": str(i.date),
                "time": str(i.time),
                "emotion": i.emotion,
                "image": i.frame.url
            }
            data.append(notification)

        # Find the emotion with the maximum count
        max_emotion = max(emotion_count, key=emotion_count.get)
        average_emotion = max_emotion

    print(data, "UUUUUUUUUUUUUUUUUUUUUU")
    response_data = {
        "notifications": len(data),
        "average_emotion": average_emotion,
        "camera": 1
    }
    response_list = [response_data]
    print(response_data, "jjjjjjjjjjjjjjjjjjjjjjjjjjjjj")
    json_response = json.dumps(response_list)
    return HttpResponse(json_response, content_type="application/json")


# def avgimag(request):
#     # Get notifications within the last 5 minutes
#     import datetime
#     import json
#     from django.http import HttpResponse
#     five_minutes_ago = datetime.datetime.now() - datetime.timedelta(minutes=5)
#     ob = videoDetails_table.objects.filter(time__gte=five_minutes_ago)
#
#     if len(ob) == 0:
#         data = []
#         average_emotion = "neutral"  # If no notifications, average emotion is neutral
#     else:
#         data = []
#         total_emotion = 0
#         num_notifications = 0
#
#         for i in ob:
#             try:
#                 total_emotion += int(i.emotion)
#                 num_notifications += 1
#             except ValueError:
#                 # Handle cases where emotion is not convertible to int
#                 pass
#
#             notification = {
#                 "cameraid": i.CAMERA.camNo,
#                 "date": str(i.date),
#                 "time": str(i.time),
#                 "emotion": i.emotion,
#                 "image": i.frame.url
#             }
#             data.append(notification)
#
#         # Calculate average emotion
#         average_emotion_value = total_emotion / num_notifications if num_notifications > 0 else 0
#
#         # Determine the corresponding emotion label based on the average emotion value
#         if average_emotion_value < 1:
#             average_emotion = "angry"
#         elif average_emotion_value < 2:
#             average_emotion = "disgust"
#         elif average_emotion_value < 3:
#             average_emotion = "fear"
#         elif average_emotion_value < 4:
#             average_emotion = "happy"
#         elif average_emotion_value < 5:
#             average_emotion = "sad"
#         elif average_emotion_value < 6:
#             average_emotion = "surprise"
#         else:
#             average_emotion = "neutral"
#
#     print(data, "UUUUUUUUUUUUUUUUUUUUUU")
#     response_data = {
#         "notifications": len(data),
#         "average_emotion": average_emotion,
#         "camera": 1
#     }
#     response_list = [response_data]
#     print(response_data, "jjjjjjjjjjjjjjjjjjjjjjjjjjjjj")
#     json_response = json.dumps(response_list)
#     return HttpResponse(json_response, content_type="application/json")
