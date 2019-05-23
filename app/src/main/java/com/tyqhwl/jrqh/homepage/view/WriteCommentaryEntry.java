package com.tyqhwl.jrqh.homepage.view;

import java.io.Serializable;

public class WriteCommentaryEntry implements Serializable {
    public String commit;

    public WriteCommentaryEntry(String commit) {
        this.commit = commit;
    }


    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }
}
