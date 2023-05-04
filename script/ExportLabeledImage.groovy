def imageData = getCurrentImageData()

// Define output path (relative to project)
def ImageOutputDir = buildFilePath(PROJECT_BASE_DIR, 'images')
def MaskOutputDir = buildFilePath(PROJECT_BASE_DIR, 'masks')
mkdirs(ImageOutputDir)
mkdirs(MaskOutputDir)
def name = GeneralTools.getNameWithoutExtension(imageData.getServer().getMetadata().getName())
def LabelPath = buildFilePath(MaskOutputDir, name + "_label.tif")
def ImagePath = buildFilePath(ImageOutputDir, name + ".tif")


// Define how much to downsample during export (may be required for large images)
double downsample = 1

// Create an ImageServer and a LabelServer where the pixels are derived from annotations
def LabelServer = new LabeledImageServer.Builder(imageData)
  .backgroundLabel(0, ColorTools.WHITE) // Specify background label (usually 0 or 255)
  .downsample(downsample)    // Choose server resolution; this should match the resolution at which tiles are exported
  .addLabel('NeuN+', 1)      // Choose output labels (the order matters!)
//  .addLabel('Soma', 2)

  .multichannelOutput(false) // If true, each label refers to the channel of a multichannel binary image (required for multiclass probability)
  .build()

'''
// Write the image
writeImage(labelServer, path)

// Write the full image downsampled by a factor of 8
def server = getCurrentServer()
def requestFull = RegionRequest.createInstance(server, 8)
writeImageRegion(server, requestFull, ImagePath)
'''

def ImageServer = getCurrentServer()


// Write the region of the image corresponding to the currently-selected object
def roi = getSelectedROI()
def requestROI = RegionRequest.createInstance(ImageServer.getPath(), 1, roi)
writeImageRegion(ImageServer, requestROI, ImagePath)
writeImageRegion(LabelServer, requestROI, LabelPath)

