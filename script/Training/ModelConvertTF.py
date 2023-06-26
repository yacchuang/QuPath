# -*- coding: utf-8 -*-
"""
Created on Mon Jun 26 08:21:09 2023

@author: Ya-Chen.Chuang
"""

from stardist.models import StarDist2D

model = StarDist2D(None, name="ProbeSegCy5-2", basedir='C:/Users/ya-chen.chuang/Documents/QuPath/models')
model.export_TF('C:/Users/ya-chen.chuang/Documents/QuPath/models/ProbeSegCy5-2')
from zipfile import ZipFile
with ZipFile('C:/Users/ya-chen.chuang/Documents/QuPath/models/ProbeSegCy5-2.zip', 'r') as zipObj:
    zipObj.extractall('c:/temp')
import os
os.system('python -m tf2onnx.convert --opset 10 --saved-model "c:/temp" --output_frozen_graph "c:/temp/ProbeSegCy5-2.pb"')