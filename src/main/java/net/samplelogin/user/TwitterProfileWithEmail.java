package net.samplelogin.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.social.twitter.api.TwitterProfile;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterProfileWithEmail extends TwitterProfile {
    private String email;

    public TwitterProfileWithEmail() {
        super(Long.MIN_VALUE, null, null, null, null, null, null, null);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
