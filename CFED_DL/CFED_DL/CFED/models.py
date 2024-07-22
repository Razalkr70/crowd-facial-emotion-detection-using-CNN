from django.db import models

# Create your models here.

class login_table(models.Model):
    username=models.CharField(max_length=20)
    password=models.CharField(max_length=20)
    type=models.CharField(max_length=20)

class user_table(models.Model):
    LOGIN=models.ForeignKey(login_table,on_delete=models.CASCADE)
    firstname=models.CharField(max_length=50)
    lastname=models.CharField(max_length=50)
    place=models.CharField(max_length=50)
    post=models.CharField(max_length=50)
    pin=models.IntegerField()
    phone=models.BigIntegerField()
    email=models.CharField(max_length=50)

class feedback_table(models.Model):
    USER= models.ForeignKey(user_table, on_delete=models.CASCADE)
    feedback=models.CharField(max_length=200)
    date=models.DateField()

class camera_table(models.Model):
    USER = models.ForeignKey(user_table, on_delete=models.CASCADE)
    camNo= models.CharField(max_length=50)
    details=models.CharField(max_length=50)

class complaint_table(models.Model):
    USER = models.ForeignKey(user_table, on_delete=models.CASCADE)
    complaint=models.CharField(max_length=200)
    reply=models.CharField(max_length=200)
    date=models.DateField()

class notification_table(models.Model):
    CAMERA=models.ForeignKey(camera_table, on_delete=models.CASCADE)
    date=models.DateField()
    time=models.TimeField()
    emotion=models.CharField(max_length=50)
    image=models.ImageField()

class video_table(models.Model):
    USER = models.ForeignKey(user_table, on_delete=models.CASCADE)
    date_time=models.DateTimeField()
    video=models.FileField()

class videoDetails_table(models.Model):
    CAMERA=models.ForeignKey(camera_table, on_delete=models.CASCADE)
    frame=models.FileField()
    emotion = models.CharField(max_length=50)
    date = models.DateField()
    time=models.TimeField()

class image_table(models.Model):
    USER=models.ForeignKey(user_table, on_delete=models.CASCADE)
    date_time=models.DateTimeField()
    image=models.FileField()

class imageDetails_table(models.Model):
    IMAGE=models.ForeignKey(image_table, on_delete=models.CASCADE)
    frame=models.FileField()
    emotion=models.CharField(max_length=50)