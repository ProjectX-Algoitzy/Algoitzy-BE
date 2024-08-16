package org.example.domain.s3_file;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileType {
    // 이미지 파일 형식
    PNG("png"),
    JPG("jpg"),
    JPEG("jpeg"),

    // 문서 파일 형식
    PDF("pdf"),
    PPT("ppt"),
    PPTX("pptx"),
    DOC("doc"),
    DOCX("docx"),
    TXT("txt"),
    CSV("csv"),
    XLS("xls"),
    XLSX("xlsx"),
    HWP("hwp"),
    HWPX("hwpx");

    private final String extension;

    public static boolean isSupported(String fileExtension) {
        for (FileType type : values()) {
            if (type.getExtension().equalsIgnoreCase(fileExtension)) {
                return true;
            }
        }
        return false;
    }
}
