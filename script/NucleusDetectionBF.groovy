import qupath.ext.stardist.StarDist2D
import qupath.lib.images.servers.ColorTransforms
import qupath.imagej.gui.ImageJMacroRunner

// Specify the model .pb file (you will need to change this!)
// def pathModel = 'script/he_heavy_augment.pb'
def pathInput = buildFilePath(PROJECT_BASE_DIR)
def pathModel = "R:/QuPath/models/stardist_model_1_channel.pb"

def stardist = StarDist2D.builder(pathModel)
        .threshold(0.9)              // Probability (detection) threshold
//      .channels('DAPI')            // Flouroscent
        .channels(
            ColorTransforms.createColorDeconvolvedChannel(getCurrentImageData().getColorDeconvolutionStains(), 1),  // Stain 1 Hematoxylin
//          ColorTransforms.createColorDeconvolvedChannel(getCurrentImageData().getColorDeconvolutionStains(), 2)
            )                        // Select detection channel
        .normalizePercentiles(1, 99) // Percentile normalization
        .pixelSize(0.5)              // Resolution for detection
        .cellExpansion(5.0)          // Approximate cells based upon nucleus expansion
        .cellConstrainScale(1.5)     // Constrain cell expansion using nucleus size
        .measureShape()              // Add shape measurements
        .measureIntensity()          // Add cell measurements (in all compartments)
        .includeProbability(true)    // Add probability as a measurement (enables later filtering)
        .build()


// Run detection for the selected objects
def imageData = getCurrentImageData()
def pathObjects = getSelectedObjects()
if (pathObjects.isEmpty()) {
    Dialogs.showErrorMessage("StarDist", "Please select a parent object!")
    return
}

// For TMA iterate through all cores
def cores = getCurrentHierarchy().getTMAGrid().getTMACoreList()

cores.each{core->
    objectsInside = getCurrentHierarchy().getObjectsForROI(qupath.lib.objects.PathDetectionObject, core.getROI())
    objectsInside.each{cell->
        cell.setName(core.getName())
    }
}


stardist.detectObjects(imageData, cores)
println 'Done!'