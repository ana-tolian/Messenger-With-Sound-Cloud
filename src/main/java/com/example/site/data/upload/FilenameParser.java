package com.example.site.data.upload;

public class FilenameParser {

    public static String getFilenameFromHref (String href) {
        return href.substring(href.lastIndexOf('/') + 1);
    }

    public static String getExtension (String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

    public static String getFileType (String extension) {
        String type = "";

        if (    extension.equalsIgnoreCase("jpeg")
                || extension.equalsIgnoreCase("jpg")
                || extension.equalsIgnoreCase("png")
                || extension.equalsIgnoreCase("gif"))
            type = "image";
        else if (extension.equalsIgnoreCase("mp3")
                || extension.equalsIgnoreCase("ogg"))
            type = "audio";
        else
            type = "file";

        return type;
    }

    public static String filenameToMD5 (String filename) {
        return MD5(filename) + "." + FilenameParser.getExtension(filename);
    }

    private static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String cleanSoundtrackFilename(String filename) {
        filename = filename.substring(0, filename.lastIndexOf('.'));
        filename = filename.replace('_', ' ');
        filename = filename.replace('-', '–');
        filename = filename.replace('—', '–');

        int count = countDashes(filename);
        if (count == 1)
            return filename;

        else if (count >= 2) {
            int index = filename.substring(filename.indexOf('–') + 1).indexOf('–');
            filename = filename.substring(0, index) + filename.substring(index + 1); // substr(0, index of second '–') + substr(index of second '–');
        }

        return filename;
    }

    private static int countDashes (String str) {
        char [] ch = str.toCharArray();
        int count = 0;

        for (int i = 0; i < ch.length; i++)
            if (ch[i] == '–')
                count++;

        return count;
    }

    public static String getSoundtrackName(String fileName) {
        return cleanSoundtrackFilename(fileName).substring(cleanSoundtrackFilename(fileName).lastIndexOf('–') + 1);
    }

    public static String getSoundtrackArtist(String fileName) {
        return cleanSoundtrackFilename(fileName).substring(0, cleanSoundtrackFilename(fileName).lastIndexOf('–'));
    }
}
