
public class AudioFile {
	
	private String pathname;
	private String filename;
	private String author;
	private String title;
	
	public AudioFile() {}
	
	public AudioFile (String path) {
		parsePathname(path);
		parseFilename(filename);
	}

	private boolean isWindows() {
		 return System.getProperty("os.name").toLowerCase()
		 .indexOf("win") >= 0;
	}
	
	public void parsePathname(String path) {
	    String temp = path.trim();
	    String slashes_changed;
	    String slashes_repetition_fixed;
	    int lastSlashIndex;

	    if ((temp.indexOf('/') == -1) && (temp.indexOf('\\') == -1)) {
	        pathname = temp.replace("‿", "").trim();
	        filename = pathname.equals("-") ? "-" : pathname;
	    } else {
	        if (isWindows()) {
	            slashes_changed = temp.replace("/", "\\");
	            slashes_repetition_fixed = removeExtraBackslashes(slashes_changed);
	            lastSlashIndex = slashes_repetition_fixed.lastIndexOf("\\");
	        } else {
	            slashes_changed = temp.replace("\\", "/");
	            slashes_repetition_fixed = removeExtraForwardSlashes(slashes_changed);
	            lastSlashIndex = slashes_repetition_fixed.lastIndexOf("/");
	        }
	        
	        pathname = slashes_repetition_fixed;
	        if (lastSlashIndex != -1) {
	            filename = pathname.substring(lastSlashIndex + 1).trim();
	        } else {
	            filename = pathname.substring(0).trim();
	        }
	    }
	}
	
	private String removeExtraBackslashes(String path) {
	    StringBuilder result = new StringBuilder();
	    boolean lastWasBackslash = false;
	    
	    for (char c : path.toCharArray()) {
	        if (c == '\\') {
	            if (!lastWasBackslash) {
	                result.append(c);
	            }
	            lastWasBackslash = true;
	        } else {
	            result.append(c);
	            lastWasBackslash = false;
	        }
	    }
	    return result.toString();
	}
	
	private String removeExtraForwardSlashes(String path) {
	    StringBuilder result = new StringBuilder();
	    boolean lastWasSlash = false;
	    
	    for (char c : path.toCharArray()) {
	        if (c == '/') {
	            if (!lastWasSlash) {
	                result.append(c);
	            }
	            lastWasSlash = true;
	        } else {
	            result.append(c);
	            lastWasSlash = false;
	        }
	    }
	    return result.toString();
	}
	
	public void parseFilename(String filename) {
	    int hyphenIndex = filename.indexOf(" - ");
	    int extensionIndex = filename.lastIndexOf('.');

	    if (filename.isEmpty()) {
	        author = "";
	        title = "";
	        return;
	    }
	    
	    if (hyphenIndex != -1) {
	        author = filename.substring(0, hyphenIndex).trim();
	        if (extensionIndex != -1) {
	            title = filename.substring(hyphenIndex + 2, extensionIndex).trim();
	        } else {
	            title = filename.substring(hyphenIndex + 2).trim();
	        }
	    } else {
	        author = "";
	        if (extensionIndex != -1) {
	            title = filename.substring(0, extensionIndex).trim();
	        } else {
	            title = filename.trim();
	        }
	    }
	}
	
	public String getPathname() {
		return pathname;
	}

	public String getFilename() {
		return filename;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}
	
	public String toString() {
		if (getAuthor() == "") {
			return title;
		} else {
			return author + " - " + title;
		}
	}
}