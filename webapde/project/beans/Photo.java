package webapde.project.beans;

import java.util.ArrayList;
import java.util.Comparator;

public class Photo {
	private int id;
	private String title;
	private String description;
	private boolean privacy; // true -> private otherwise public
	private String format;
	private String URL;
	private String owner;
	private ArrayList<String> tags;
	private int ownerId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public boolean isPrivacy() {
		return privacy;
	}
	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public void setOwnerId(int ownerId){
		this.ownerId = ownerId;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public String getURLFormat() {
		return URL+format;
	}
	
}
