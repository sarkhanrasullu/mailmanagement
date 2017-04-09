/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailmanagement.beans;

/**
 *
 * @author Afgan Rasulov
 */
public class MailBean {
    private String subject;
    private String message;
    private String to;

    public MailBean() {
    }

    public MailBean(String subject, String message, String to) {
        this.subject = subject;
        this.message = message;
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "MailBean{" + "subject=" + subject + ", message=" + message + ", to=" + to + '}';
    }

}
