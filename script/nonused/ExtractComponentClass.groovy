def pathObject = getSelectedObject()
def pathClass = pathObject.getPathClass()
def parts = PathClassTools.splitNames(pathObject.getPathClass())

println(parts)