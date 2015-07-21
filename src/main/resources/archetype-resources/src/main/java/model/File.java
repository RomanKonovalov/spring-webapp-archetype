#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String friendlyName;

    @Column(nullable = false)
    private String name;

    @Column
    private String contentType;

    @Column(nullable = false)
    private Long size;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;

    /**
     * @return the friendlyName
     */
    public String getFriendlyName() {
        return friendlyName;
    }

    /**
     * @param friendlyName
     *            the friendlyName to set
     */
    public void setFriendlyName(final String friendlyName) {
        this.friendlyName = friendlyName;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType
     *            the contentType to set
     */
    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return the size
     */
    public Long getSize() {
        return size;
    }

    /**
     * @param size
     *            the size to set
     */
    public void setSize(final Long size) {
        this.size = size;
    }

    /**
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @param account
     *            the account to set
     */
    public void setAccount(final Account account) {
        this.account = account;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object${symbol_pound}hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((account == null) ? 0 : account.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object${symbol_pound}equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final File other = (File) obj;
        if (account == null) {
            if (other.account != null) {
                return false;
            }
        } else if (!account.equals(other.account)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object${symbol_pound}toString()
     */
    @Override
    public String toString() {
        return "File [friendlyName=" + friendlyName + ", name=" + name + ", contentType=" + contentType + ", size="
                + size + "]";
    }

}
