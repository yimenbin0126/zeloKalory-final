package com.zerocalorie.community.controller;

	import java.io.File;
	import java.io.FileInputStream;
	import java.io.IOException;
	import java.io.OutputStream;

	import javax.servlet.ServletException;
	import javax.servlet.http.HttpServletResponse;

	import org.springframework.stereotype.Controller;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;


	@Controller
	public class t_FileDownloadController {
		private static final String ARTICLE_IMAGE_REPO = "C://zerokalory_file";
		@RequestMapping("/download_t.do")
		protected void download(@RequestParam("imageFileName") String imageFileName,
								@RequestParam("articleNO") String articleNO,
				                HttpServletResponse response)throws Exception {
			OutputStream out = response.getOutputStream();
			String downFile = ARTICLE_IMAGE_REPO + "\\" +articleNO+"\\"+ imageFileName;
			File file = new File(downFile);

			response.setHeader("Cache-Control", "no-cache");
			response.addHeader("Content-disposition", "attachment; articleNO" + imageFileName);
			FileInputStream in = new FileInputStream(file);
			byte[] buffer = new byte[1024 * 8];
			while (true) {
				int count = in.read(buffer); 
				if (count == -1) 
					break;
				out.write(buffer, 0, count);
			}
			in.close();
			out.close();
		}

	}
