package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class CompanyEntity {
        private String companyName;
        private String password;
        private Double currentBill;
        private List<TweetEntity> tweets;

        public CompanyEntity() {
        }

        public CompanyEntity(String companyName, String password,
                        Double currentBill) {
                this.companyName = companyName;
                this.password = password;
                this.currentBill = currentBill;
                this.tweets = new ArrayList<TweetEntity>();
        }

        @Id
        public String getCompanyName() {
                return companyName;
        }

        @Column
        public String getPassword() {
                return password;
        }

        @Column
        public Double getCurrentBill() {
                return currentBill;
        }

        @OneToMany
        public List<TweetEntity> getTweets() {
                return tweets;
        }

        public void setCompanyName(String companyName) {
                this.companyName = companyName;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public void setCurrentBill(Double currentBill) {
                this.currentBill = currentBill;
        }

        public void setTweets(List<TweetEntity> tweets) {
                this.tweets = tweets;
        }

}
