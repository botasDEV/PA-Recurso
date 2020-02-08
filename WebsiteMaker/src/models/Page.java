/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Rafae
 */
public class Page {
    private String title;
    private String content;
    private String filename;
    private String folder;

    public Page(String title, String filename) {
        this(title, filename, "", "");
    }
    
    public Page(String title, String filename, String folder, String content) {
        this.title = title;
        this.filename = filename;
        this.content = content;
        this.folder = folder;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    @Override
    public boolean equals(Object obj) {
        return (
            obj instanceof Page
            && ((Page)obj).getFilename().equals(filename)
        );
    }

    
    
    
    
}
