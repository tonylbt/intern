package com.libt.intern.samples.recycler.bean;

import java.util.List;

/**
 * Created by qwe on 2018/8/6.
 */

public class GsonBean {
	public List<DataBean> data;

	public List<DataBean> getData() {
		return data;
	}

	public void setData(List<DataBean> data) {
		this.data = data;
	}
	public static class DataBean {
		/**
		 * bonus : 0
		 * level : 2
		 * pkId : 268
		 * themeId : 1
		 * themeName : Indian  Dancer
		 * work1 : {"video":{"duration":11,"height":960,"size":0,"thumbnailUrl":"http://cdn.weshow.me/sg/image/AKlV/8dbb7cffadb73ac1cff28f97c0285594.jpg","userId":"AKlV","videoId":"AKlV3NR40J","videoUrl":"http://cdn.weshow.me/sg/video/AKlV/360/8dbb7cffadb73ac1cff28f97c0285594.mp4","width":540},"voteCount":23,"user":{"avatarUrl":"http://cdn.weshow.me/sg/avatar/AKlV/yDnUMDsf.jpg","nickname":"sankett252","userId":"AKlV"}}
		 * work2 : {"video":{"duration":6,"height":960,"size":0,"thumbnailUrl":"http://cdn.weshow.me/sg/image/C7xJ/4456286a4185f69fb23f2f46d2521d42.jpg","userId":"C7xJ","videoId":"C7xJ3NEE9j","videoUrl":"http://cdn.weshow.me/sg/video/C7xJ/360/4456286a4185f69fb23f2f46d2521d42.mp4","width":540},"voteCount":0,"user":{"nickname":"Avneet Kaur","userId":"C7xJ"}}
		 */

		private int bonus;
		private int level;
		private String pkId;
		private String themeId;
		private String themeName;
		private Work1Bean work1;
		private Work2Bean work2;

		public int getBonus() {
			return bonus;
		}

		public void setBonus(int bonus) {
			this.bonus = bonus;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public String getPkId() {
			return pkId;
		}

		public void setPkId(String pkId) {
			this.pkId = pkId;
		}

		public String getThemeId() {
			return themeId;
		}

		public void setThemeId(String themeId) {
			this.themeId = themeId;
		}

		public String getThemeName() {
			return themeName;
		}

		public void setThemeName(String themeName) {
			this.themeName = themeName;
		}

		public Work1Bean getWork1() {
			return work1;
		}

		public void setWork1(Work1Bean work1) {
			this.work1 = work1;
		}

		public Work2Bean getWork2() {
			return work2;
		}

		public void setWork2(Work2Bean work2) {
			this.work2 = work2;
		}

		public static class Work1Bean {
			/**
			 * video : {"duration":11,"height":960,"size":0,"thumbnailUrl":"http://cdn.weshow.me/sg/image/AKlV/8dbb7cffadb73ac1cff28f97c0285594.jpg","userId":"AKlV","videoId":"AKlV3NR40J","videoUrl":"http://cdn.weshow.me/sg/video/AKlV/360/8dbb7cffadb73ac1cff28f97c0285594.mp4","width":540}
			 * voteCount : 23
			 * user : {"avatarUrl":"http://cdn.weshow.me/sg/avatar/AKlV/yDnUMDsf.jpg","nickname":"sankett252","userId":"AKlV"}
			 */

			private VideoBean video;
			private int voteCount;
			private UserBean user;

			public VideoBean getVideo() {
				return video;
			}

			public void setVideo(VideoBean video) {
				this.video = video;
			}

			public int getVoteCount() {
				return voteCount;
			}

			public void setVoteCount(int voteCount) {
				this.voteCount = voteCount;
			}

			public UserBean getUser() {
				return user;
			}

			public void setUser(UserBean user) {
				this.user = user;
			}

			public static class VideoBean {
				/**
				 * duration : 11
				 * height : 960
				 * size : 0
				 * thumbnailUrl : http://cdn.weshow.me/sg/image/AKlV/8dbb7cffadb73ac1cff28f97c0285594.jpg
				 * userId : AKlV
				 * videoId : AKlV3NR40J
				 * videoUrl : http://cdn.weshow.me/sg/video/AKlV/360/8dbb7cffadb73ac1cff28f97c0285594.mp4
				 * width : 540
				 */

				private int duration;
				private int height;
				private int size;
				private String thumbnailUrl;
				private String userId;
				private String videoId;
				private String videoUrl;
				private int width;

				public int getDuration() {
					return duration;
				}

				public void setDuration(int duration) {
					this.duration = duration;
				}

				public int getHeight() {
					return height;
				}

				public void setHeight(int height) {
					this.height = height;
				}

				public int getSize() {
					return size;
				}

				public void setSize(int size) {
					this.size = size;
				}

				public String getThumbnailUrl() {
					return thumbnailUrl;
				}

				public void setThumbnailUrl(String thumbnailUrl) {
					this.thumbnailUrl = thumbnailUrl;
				}

				public String getUserId() {
					return userId;
				}

				public void setUserId(String userId) {
					this.userId = userId;
				}

				public String getVideoId() {
					return videoId;
				}

				public void setVideoId(String videoId) {
					this.videoId = videoId;
				}

				public String getVideoUrl() {
					return videoUrl;
				}

				public void setVideoUrl(String videoUrl) {
					this.videoUrl = videoUrl;
				}

				public int getWidth() {
					return width;
				}

				public void setWidth(int width) {
					this.width = width;
				}
			}

			public static class UserBean {
				/**
				 * avatarUrl : http://cdn.weshow.me/sg/avatar/AKlV/yDnUMDsf.jpg
				 * nickname : sankett252
				 * userId : AKlV
				 */

				private String avatarUrl;
				private String nickname;
				private String userId;

				public String getAvatarUrl() {
					return avatarUrl;
				}

				public void setAvatarUrl(String avatarUrl) {
					this.avatarUrl = avatarUrl;
				}

				public String getNickname() {
					return nickname;
				}

				public void setNickname(String nickname) {
					this.nickname = nickname;
				}

				public String getUserId() {
					return userId;
				}

				public void setUserId(String userId) {
					this.userId = userId;
				}
			}
		}

		public static class Work2Bean {
			/**
			 * video : {"duration":6,"height":960,"size":0,"thumbnailUrl":"http://cdn.weshow.me/sg/image/C7xJ/4456286a4185f69fb23f2f46d2521d42.jpg","userId":"C7xJ","videoId":"C7xJ3NEE9j","videoUrl":"http://cdn.weshow.me/sg/video/C7xJ/360/4456286a4185f69fb23f2f46d2521d42.mp4","width":540}
			 * voteCount : 0
			 * user : {"nickname":"Avneet Kaur","userId":"C7xJ"}
			 */

			private VideoBeanX video;
			private int voteCount;
			private UserBeanX user;

			public VideoBeanX getVideo() {
				return video;
			}

			public void setVideo(VideoBeanX video) {
				this.video = video;
			}

			public int getVoteCount() {
				return voteCount;
			}

			public void setVoteCount(int voteCount) {
				this.voteCount = voteCount;
			}

			public UserBeanX getUser() {
				return user;
			}

			public void setUser(UserBeanX user) {
				this.user = user;
			}

			public static class VideoBeanX {
				/**
				 * duration : 6
				 * height : 960
				 * size : 0
				 * thumbnailUrl : http://cdn.weshow.me/sg/image/C7xJ/4456286a4185f69fb23f2f46d2521d42.jpg
				 * userId : C7xJ
				 * videoId : C7xJ3NEE9j
				 * videoUrl : http://cdn.weshow.me/sg/video/C7xJ/360/4456286a4185f69fb23f2f46d2521d42.mp4
				 * width : 540
				 */

				private int duration;
				private int height;
				private int size;
				private String thumbnailUrl;
				private String userId;
				private String videoId;
				private String videoUrl;
				private int width;

				public int getDuration() {
					return duration;
				}

				public void setDuration(int duration) {
					this.duration = duration;
				}

				public int getHeight() {
					return height;
				}

				public void setHeight(int height) {
					this.height = height;
				}

				public int getSize() {
					return size;
				}

				public void setSize(int size) {
					this.size = size;
				}

				public String getThumbnailUrl() {
					return thumbnailUrl;
				}

				public void setThumbnailUrl(String thumbnailUrl) {
					this.thumbnailUrl = thumbnailUrl;
				}

				public String getUserId() {
					return userId;
				}

				public void setUserId(String userId) {
					this.userId = userId;
				}

				public String getVideoId() {
					return videoId;
				}

				public void setVideoId(String videoId) {
					this.videoId = videoId;
				}

				public String getVideoUrl() {
					return videoUrl;
				}

				public void setVideoUrl(String videoUrl) {
					this.videoUrl = videoUrl;
				}

				public int getWidth() {
					return width;
				}

				public void setWidth(int width) {
					this.width = width;
				}
			}

			public static class UserBeanX {
				/**
				 * nickname : Avneet Kaur
				 * userId : C7xJ
				 */

				private String nickname;
				private String userId;

				public String getNickname() {
					return nickname;
				}

				public void setNickname(String nickname) {
					this.nickname = nickname;
				}

				public String getUserId() {
					return userId;
				}

				public void setUserId(String userId) {
					this.userId = userId;
				}
			}
		}
	}
}
