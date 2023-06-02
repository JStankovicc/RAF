package com.example.domaciwp6.model;

public class Comment {
        private int id;
        private int postId;
        private String author;
        private String comment;

        public Comment(){}

        public Comment(int postId,String author, String comment) {
            this.postId = postId;
            this.author = author;
            this.comment = comment;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
}

