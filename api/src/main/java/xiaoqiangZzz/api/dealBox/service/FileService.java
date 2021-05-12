package xiaoqiangZzz.api.dealBox.service;

import xiaoqiangZzz.api.dealBox.entity.File;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件服务.
 */
public interface FileService {
  String CONFIG_PATH = AttachmentService.CONFIG_PATH;
  void download(String filename, File file, HttpServletResponse response);
}
