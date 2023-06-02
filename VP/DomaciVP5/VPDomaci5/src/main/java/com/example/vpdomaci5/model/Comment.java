package com.example.vpdomaci5.model;

public class Comment {
        private String author;
        private String comment;

        public Comment(String author, String comment) {
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

