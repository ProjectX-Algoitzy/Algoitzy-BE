package org.example.domain.s3_file.enums;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileExtension {

  ZIP("zip"),
  PDF("pdf"),
  HWP("hwp"),
  HWPX("hwpx"),
  DOC("doc"),
  DOCX("docx"),
  PPT("ppt"),
  PPTX("pptx"),
  TXT("txt"),
  JPG("jpg"),
  JPEG("jpeg"),
  PNG("png"),
  GIF("gif"),
  SVG("svg");

  private final String extension;

  public static boolean exist(String extension) {
    return Arrays.stream(FileExtension.values()).anyMatch(e -> e.extension.equals(extension));
  }
}
