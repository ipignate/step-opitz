package utils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import entities.AbstractEntity;
import entities.ClosedShell;

public class StepFileReader {

	private Map<String, String> linesMap;

	public StepFileReader(String fileName) {
		BufferedReader br = null;
		linesMap = new HashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		String cStream = "";
	
		try {
				br = new BufferedReader(new FileReader(fileName));
				try {
						while((cStream = br.readLine()) != null) {
							sb.append(cStream);
						} 
				}
				catch (IOException e){
					e.printStackTrace();
				}
				// Remove newlines characters
				String data = sb.toString().replace('\n', ' ');
				// Split data into array of lines
				String[] line = data.split(";");
				
				int max = line.length;
				int i=0;
				while (i < max) {
					if (line[i].startsWith("#")) {
						String arr[] = line[i].split("=");
						// lineNum == '#10'
						String lineNum = arr[0].trim();
						// lineVal == 'AXIS2_PLACEMENT_3D ( 'NONE', #135, #136, #137 )'
						String lineVal = arr[1].substring(0, arr[1].length() - 1).trim();
						linesMap.put(lineNum, lineVal);
					}
					i++;
				}
		} catch (FileNotFoundException e) {
				e.printStackTrace();
		}
		AbstractEntity.linesMap = linesMap;
	}
	
	public Map<String, String> getLinesMap() {
		return linesMap;
	}	
	
	public String getClosedShellLineId() {
		int i = 0;
		for (Entry<String, String> e : linesMap.entrySet()) {
			if (e.getValue().startsWith(ClosedShell._CLOSED_SHELL)) {
				if (++i > 1) {
					throw new RuntimeException("more than one CLOSED_SHELL");
				}
				return e.getKey();
			}
		}		
		return null;
	}

}
