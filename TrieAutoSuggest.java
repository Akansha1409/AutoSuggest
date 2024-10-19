import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord = false;

    public TrieNode() {
    }
}

class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Insert a word into the trie
    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current = current.children.computeIfAbsent(c, k -> new TrieNode());
        }
        current.isEndOfWord = true;
    }

    // Search for a prefix in the trie and return possible auto-suggestions
    public List<String> autoSuggest(String prefix) {
        TrieNode current = root;
        for (char c : prefix.toCharArray()) {
            current = current.children.get(c);
            if (current == null) {
                return new ArrayList<>();
            }
        }
        List<String> suggestions = new ArrayList<>();
        collectSuggestions(current, new StringBuilder(prefix), suggestions);
        return suggestions;
    }

    // Helper function to collect all words under a given node
    private void collectSuggestions(TrieNode node, StringBuilder prefix, List<String> suggestions) {
        if (node.isEndOfWord) {
            suggestions.add(prefix.toString());
        }
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            prefix.append(entry.getKey());
            collectSuggestions(entry.getValue(), prefix, suggestions);
            prefix.deleteCharAt(prefix.length() - 1); // backtrack
        }
    }
}

public class TrieAutoSuggest {
    public static void main(String[] args) {
        Trie trie = new Trie();
        
        // Inserting words into Trie
        trie.insert("hello");
        trie.insert("hell");
        trie.insert("helicopter");
        trie.insert("help");
        trie.insert("hero");
        trie.insert("her");

        // Auto-suggest for a prefix
        String prefix = "he";
        System.out.println("Auto-suggestions for prefix '" + prefix + "':");
        List<String> suggestions = trie.autoSuggest(prefix);
        for (String suggestion : suggestions) {
            System.out.println(suggestion);
        }
    }
}

