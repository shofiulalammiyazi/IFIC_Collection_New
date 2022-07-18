package com.csinfotechbd.workflow.bpmn;

import org.springframework.stereotype.Component;

@Component
public class FileIO {

//	@Value("${activiti-temp-resource}")
//	private String SOURCE;
//
//	public String getBpmnFile(String fileName) {
//
//		String document = null;
//
//		FileInputStream fis = null;
//		try {
//			fis = new FileInputStream(SOURCE + fileName);
//			document = IOUtils.toString(fis);
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		} finally {
//			try {
//				if (fis != null)
//					fis.close();
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
//		return document;
//	}
//
//	public Map<String, Object> readVariable() {
//		FileReader fr = null;
//		BufferedReader br = null;
//		Map<String, Object> map = null;
//
//		try {
//			fr = new FileReader(SOURCE + "variable.txt");
//			br = new BufferedReader(fr);
//			map = new HashMap<String, Object>();
//			String line = br.readLine();
//			while (line != null) {
//				String[] v = line.split("=");
//				map.put(v[0], v[1]);
//				line = br.readLine();
//			}
//
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		} finally {
//			try {
//				if (fr != null)
//					fr.close();
//				if (br != null)
//					br.close();
//			} catch (IOException ex) {
//				// TODO Auto-generated catch block
//				ex.printStackTrace();
//			}
//		}
//		return map;
//	}
}
