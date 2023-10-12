def imageData = getCurrentImageData()

// Define output path (relative to project)
def ImageOutputDir = buildFilePath(PROJECT_BASE_DIR, 'images')
mkdirs(ImageOutputDir)

def name = GeneralTools.getNameWithoutExtension(imageData.getServer().getMetadata().getName())
def ImagePath = buildFilePath(ImageOutputDir, name + "_test.tif")

def ImageServer = getCurrentServer()


// Write the region of the image corresponding to the currently-selected object
def roi = getSelectedROI()
def requestROI = RegionRequest.createInstance(ImageServer.getPath(), 1, roi)
writeImageRegion(ImageServer, requestROI, ImagePath)