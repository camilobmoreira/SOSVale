package hello;

import java.util.Comparator;
import java.util.Date;

public class Post implements Comparator<Date> {
	
	private String title;
	private String description;
	private Location location;
	private Image image;
	private String username;
	private String postType;
	private Date postingDate;
	private boolean approved = false;
	
	public Post () {
		super();
		this.approved = false;
	}

	public Post(String title, String description, Location location, Image image, String username, String postType,
			Date postingDate) {
		super();
		this.title = title;
		this.description = description;
		this.location = location;
		this.image = image;
		this.username = username;
		this.postType = postType;
		this.postingDate = postingDate;
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public Date getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@Override
	public int compare(Date d1, Date d2) {
		// TODO Auto-generated method stub
		return d1.compareTo(d2);
	}
}
