package com.unisoft.utillity;

import org.springframework.stereotype.Component;

@Component
public class ImageSaver {


	/*@Autowired
	private ServletContext servletContext;*/

//    @Value("${image.location}")
//    public String IMAGE_DIRECTORY;

//    public String saveImageToFolder(MultipartFile file) {
//
//        String imageName = UUID.randomUUID().toString();
//        String fileExtention = FilenameUtils.getExtension(file.getOriginalFilename());
//        //Absolute path where the file should save
//        //String absolutePath = ServletContextListener.class.getClassLoader().getResource("static"+File.separator+"img"+File.separator).getPath()+folderName+File.separator;
//        //String realPath = servletContext.getRealPath("/")+"extras"+File.separator+"images"+File.separator;
//
////        String folder = IMAGE_DIRECTORY+File.separator;
//        File temp = new File(folder);
//        if(!temp.exists()){
//            temp.mkdirs();
//        }
//        //String databaseUrl = File.separator + "img" + File.separator + folderName + File.separator + imageName + "." + fileExtention;
//
//        String modifiedDbUrl = File.separator + "images"+ File.separator+imageName;
//        String fileNameWithExt = imageName + "." + fileExtention;
//
//        //if(absolutePath==null)throw new NullPointerException("absolute path");
//
//        //System.out.println(absolutePath);
//
//        try {
//            File f2 = new File(folder+fileNameWithExt);
//            file.transferTo(f2);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            System.out.println(e.getMessage());
//        }
//
//        return modifiedDbUrl;//databaseUrl;
//
//    }


}
