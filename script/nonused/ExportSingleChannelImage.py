# -*- coding: utf-8 -*-
"""
Created on Fri Mar 10 16:41:42 2023

@author: Ya-Chen.Chuang
"""

import os
vipsbin = r'C:\Users\ya-chen.chuang\vips-dev-8.14\bin'
os.environ['PATH'] = vipsbin + ';' + os.environ['PATH']


import pyvips as vips


# image = vips.Image.new_from_file("SP-014200_1501-1F-CS-DRG1_EGFP-C1_mCherry-C2_MfaRBFOX3-C3_MfaGFAP-C4_yrwg_01.tif", access='sequential')
# image.write_to_file("dapi.tiff", pyramid=True, tile=True,  bigtiff=True, subifd=False, compression="jpeg", Q=90,tile_height=512, tile_width=512)
image = vips.Image.tiffload("SP-014200_1501-1F-CS-DRG1_EGFP-C1_mCherry-C2_MfaRBFOX3-C3_MfaGFAP-C4_yrwg_01.tif", n=5)
image.tiffsave("SP-014200_1501-1F-CS-DRG1.tiff", pyramid=False, compression="jpeg", tile=True, tile_width=512, tile_height=512)
RBFOX = image[0]
RBFOX.write_to_file("SP-014200_1501-1F-CS-DRG1.tiff", pyramid=True, tile=True, compression="jpeg")