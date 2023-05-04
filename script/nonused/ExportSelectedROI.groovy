// Export_to_IJ.groovy

import ij.ImageJ
import qupath.lib.scripting.QP
import qupath.lib.gui.scripting.QPEx
import qupath.imagej.tools.IJTools
import ij.plugin.frame.RoiManager
import ij.gui.Roi
import qupath.imagej.gui.IJExtension
import qupath.lib.regions.RegionRequest

def viewer = QPEx.getCurrentViewer()
def server = viewer.getImageData().getServer()

// Select an annotation
def anno = QP.getSelectedObject()

if (anno == null){
    print 'No annotation selected!\n'
    return
}
def annoRoi = anno.getROI()
def annoX = annoRoi.boundsX
def annoY = annoRoi.boundsY
def region = RegionRequest.createInstance(server.getPath(), 1, annoRoi);

def imp = IJExtension.extractROIWithOverlay(server, anno, null, region, false, null).getImage();

ImageJ ij = IJExtension.getImageJInstance();
if (ij != null) {
    ij.setVisible(true);
    imp.show()
}

RoiManager roiMan = RoiManager.getRoiManager()
roiMan.setVisible(true)

// Collect all detections in the hierarchy below the selected annotation
def detectionsInThisAnnotation = viewer.getHierarchy().getObjectsForROI(qupath.lib.objects.PathDetectionObject, annoRoi)

detectionsInThisAnnotation.each { detect ->
    def roi = detect.getROI()

    def x = roi.boundsX - annoX
    def y = roi.boundsY - annoY

    Roi roiIJ = IJTools.convertToIJRoi(roi, 0, 0, 1)
    roiIJ.setLocation(x, y)
    roiMan.addRoi(roiIJ)
}

// Save IJ Rois (optional)
//def filePath = QPEx.buildFilePath(QPEx.PROJECT_BASE_DIR, 'rois.zip')
//roiMan.runCommand("Save", filePath)

println('Done!')