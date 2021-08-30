package learn.bgspr5.ch09.common;

/**
 * Provides a single method for converting to wildcard.
 * The reason this class exists is because some databases use special characters to match wildcards.
 * For example SQL uses %, while Neo4J uses a regular expression (like ".*").
 * Other databases might use different characters or nothing at all.
 */
public class WildCardConverter {
    private final String append;

    public WildCardConverter(String append) {
        this.append = append;
    }

    public String convertToWildCard(String data) {
        return data + append;
    }
}
