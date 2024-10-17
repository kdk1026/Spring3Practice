package kr.co.test.common.spring.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import common.BaseObject;
import common.util.ResponseUtil;
import common.util.file.FileUtil;
import common.util.string.StringUtilsSub;

public class Spring3FileUtil {

	private Spring3FileUtil() {
		super();
	}
	
	private static final Logger logger = LoggerFactory.getLogger(Spring3FileUtil.class);
	
	public static class FileVO extends BaseObject {
		
		private static final long serialVersionUID = 1L;

		public FileVO() {
			super();
		}
		
		/**
		 * 파일 경로
		 */
		public String destFilePath;
		
		/**
		 * 파일 확장자
		 */
		public String fileExt;
		
		/**
		 * 원파일명
		 */
		public String orignlFileNm;
		
		/**
		 * 저장파일명
		 */
		public String saveFileNm;
		
		/**
		 * 파일 크기
		 */
		public long fileSize;
		
		/**
		 * 파일 크기 단위
		 */
		public String fileSizeUnits;
	}
	
	/**
	 * <pre>
	 * Spring 3 파일 업로드
	 *  - Apache Commons FileUpload 필요
	 *  - 파일 업로드 전 파일 확장자 및 MIME Type 체크 진행할 것
	 * </pre>
	 * @param multipartFile
	 * @param destFilePath
	 * @return
	 */
	public static FileVO uploadFile(MultipartFile multipartFile, String destFilePath) {
		destFilePath = (destFilePath.replaceAll("^(.*)(.$)", "$2").equals("/")) ? destFilePath : (destFilePath + FileUtil.FOLDER_SEPARATOR);
		File destFile = new File(destFilePath);
		if (!destFile.exists()) {
			destFile.mkdirs();
		}
		
		StringBuilder sb = new StringBuilder();
		String fileExt = FileUtil.EXTENSION_SEPARATOR + FileUtil.getFileExtension(multipartFile.getOriginalFilename());
		
		sb.append(StringUtilsSub.getRandomString()).append(fileExt);
		String saveFileNm = sb.toString();
		
		sb.setLength(0);
		sb.append(destFilePath).append(FileUtil.FOLDER_SEPARATOR).append(saveFileNm);
		
		File targetFile = new File(sb.toString());
		FileVO fileVO = null;
		
		try {
			multipartFile.transferTo(targetFile);
			
			fileVO = new FileVO();
			fileVO.destFilePath = destFilePath;
			fileVO.orignlFileNm = multipartFile.getOriginalFilename();
			fileVO.saveFileNm = saveFileNm;
			fileVO.fileExt = fileExt;
			fileVO.fileSize = multipartFile.getSize();
			fileVO.fileSizeUnits = FileUtil.readableFileSize(fileVO.fileSize);
			
		} catch (IllegalStateException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}
		
		return fileVO;
	}
	
	/**
	 * Spring 3 파일 다운로드
	 * @param fileVO
	 * @param request
	 * @param response
	 */
	public static void downloadFile(FileVO fileVO, HttpServletRequest request, HttpServletResponse response) {
		String downloadlFileNm = "";
		
		String destFilePath = fileVO.destFilePath;
		destFilePath = (destFilePath.replaceAll("^(.*)(.$)", "$2").equals("/")) ? destFilePath : (destFilePath + FileUtil.FOLDER_SEPARATOR);
		
		String saveFileNm = fileVO.saveFileNm;
		String orignlFileNm = fileVO.orignlFileNm;
		
		if ( !StringUtils.hasText(orignlFileNm) ) {
			downloadlFileNm = ResponseUtil.contentDisposition(request, saveFileNm);
		} else {
			downloadlFileNm = ResponseUtil.contentDisposition(request, orignlFileNm);
		}
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Transfer-Encoding", "binary;");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadlFileNm + "\";");
		
		FileInputStream fis = null;
		InputStream is = null;
		OutputStream os = null;
		
		try {
			fis = new FileInputStream(destFilePath + saveFileNm);
			is = new BufferedInputStream(fis);
			os = response.getOutputStream();
			
			FileCopyUtils.copy(is, os);
			
		} catch (FileNotFoundException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
	}
	
	/**
	 * Spring MultipartFile -> Java File 변환
	 * @param multipart
	 * @return
	 */
	public static File multipartFileToFile(MultipartFile multipartFile) {
		File convFile = new File(multipartFile.getOriginalFilename());
		
		try {
			if ( !convFile.createNewFile() ) {
				return null;
			}
			
		} catch (IOException e) {
			logger.error("", e);
		}
		
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(convFile);
			fos.write(multipartFile.getBytes());
			
		} catch (FileNotFoundException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
        
        return convFile;
	}
	
}
