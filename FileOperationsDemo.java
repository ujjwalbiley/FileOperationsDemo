import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * FileOperationsDemo - A comprehensive demonstration of file operations in Java
 * This program showcases reading, writing, and modifying text files with error handling
 */
public class FileOperationsDemo {
    
    /**
     * Main method to demonstrate all file operations
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = "demo_file.txt";
        
        System.out.println("=== JAVA FILE OPERATIONS DEMONSTRATION ===\n");
        
        try {
            // 1. Create and write initial content to file
            System.out.println("1. CREATING AND WRITING TO FILE");
            createAndWriteFile(fileName, "Hello, World!\nThis is a sample text file.\nJava File Operations Demo\n");
            
            // 2. Read and display file content
            System.out.println("\n2. READING FILE CONTENT:");
            readFile(fileName);
            
            // 3. Append additional content
            System.out.println("\n3. APPENDING TO FILE:");
            appendToFile(fileName, "Appended line 1\nAppended line 2\n");
            readFile(fileName);
            
            // 4. Modify specific lines
            System.out.println("\n4. MODIFYING SPECIFIC LINES:");
            modifyLine(fileName, 1, "This is a MODIFIED sample text file.");
            readFile(fileName);
            
            // 5. Search and replace text
            System.out.println("\n5. SEARCH AND REPLACE:");
            searchAndReplace(fileName, "Demo", "Demonstration");
            readFile(fileName);
            
            // 6. Count lines and words
            System.out.println("\n6. FILE STATISTICS:");
            displayFileStats(fileName);
            
            // 7. Copy file
            System.out.println("\n7. COPYING FILE:");
            String copyFileName = "demo_file_copy.txt";
            copyFile(fileName, copyFileName);
            System.out.println("File copied successfully to: " + copyFileName);
            
            // 8. Delete file (optional - uncomment to enable)
            // System.out.println("\n8. CLEANUP:");
            // deleteFile(copyFileName);
            
        } catch (IOException e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
        
        System.out.println("\n=== FILE OPERATIONS COMPLETED ===");
    }
    
    /**
     * Creates a new file and writes content to it
     * @param fileName Name of the file to create
     * @param content Content to write to the file
     * @throws IOException If an I/O error occurs
     */
    public static void createAndWriteFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            System.out.println("File created and written successfully: " + fileName);
        }
    }
    
    /**
     * Reads and displays the content of a file
     * @param fileName Name of the file to read
     * @throws IOException If an I/O error occurs
     */
    public static void readFile(String fileName) throws IOException {
        if (!Files.exists(Path.of(fileName))) {
            System.out.println("File does not exist: " + fileName);
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 1;
            
            System.out.println("Content of " + fileName + ":");
            System.out.println("----------------------------------------");
            while ((line = reader.readLine()) != null) {
                System.out.printf("%2d: %s%n", lineNumber++, line);
            }
            System.out.println("----------------------------------------");
        }
    }
    
    /**
     * Appends content to an existing file
     * @param fileName Name of the file to append to
     * @param content Content to append
     * @throws IOException If an I/O error occurs
     */
    public static void appendToFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content);
            System.out.println("Content appended successfully to: " + fileName);
        }
    }
    
    /**
     * Modifies a specific line in the file
     * @param fileName Name of the file to modify
     * @param lineNumber Line number to modify (1-based)
     * @param newContent New content for the specified line
     * @throws IOException If an I/O error occurs
     */
    public static void modifyLine(String fileName, int lineNumber, String newContent) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(fileName));
        
        if (lineNumber < 1 || lineNumber > lines.size()) {
            System.out.println("Invalid line number: " + lineNumber);
            return;
        }
        
        lines.set(lineNumber - 1, newContent);
        Files.write(Path.of(fileName), lines);
        System.out.println("Line " + lineNumber + " modified successfully.");
    }
    
    /**
     * Searches for text and replaces it throughout the file
     * @param fileName Name of the file to modify
     * @param searchText Text to search for
     * @param replaceText Text to replace with
     * @throws IOException If an I/O error occurs
     */
    public static void searchAndReplace(String fileName, String searchText, String replaceText) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(fileName));
        List<String> modifiedLines = new ArrayList<>();
        
        for (String line : lines) {
            modifiedLines.add(line.replace(searchText, replaceText));
        }
        
        Files.write(Path.of(fileName), modifiedLines);
        System.out.println("Replaced all occurrences of '" + searchText + "' with '" + replaceText + "'");
    }
    
    /**
     * Displays statistics about the file (line count, word count, character count)
     * @param fileName Name of the file to analyze
     * @throws IOException If an I/O error occurs
     */
    public static void displayFileStats(String fileName) throws IOException {
        if (!Files.exists(Path.of(fileName))) {
            System.out.println("File does not exist: " + fileName);
            return;
        }
        
        String content = Files.readString(Path.of(fileName));
        long lineCount = content.lines().count();
        long wordCount = Arrays.stream(content.split("\\s+"))
                             .filter(word -> !word.isEmpty())
                             .count();
        long charCount = content.length();
        
        System.out.println("File: " + fileName);
        System.out.println("Lines: " + lineCount);
        System.out.println("Words: " + wordCount);
        System.out.println("Characters: " + charCount);
    }
    
    /**
     * Copies a file to a new location
     * @param sourceFile Source file name
     * @param destFile Destination file name
     * @throws IOException If an I/O error occurs
     */
    public static void copyFile(String sourceFile, String destFile) throws IOException {
        Files.copy(Path.of(sourceFile), Path.of(destFile), StandardCopyOption.REPLACE_EXISTING);
    }
    
    /**
     * Deletes a file
     * @param fileName Name of the file to delete
     * @throws IOException If an I/O error occurs
     */
    public static void deleteFile(String fileName) throws IOException {
        Files.deleteIfExists(Path.of(fileName));
        System.out.println("File deleted: " + fileName);
    }
    
    /**
     * Reads file content and returns as a String
     * @param fileName Name of the file to read
     * @return File content as String
     * @throws IOException If an I/O error occurs
     */
    public static String readFileToString(String fileName) throws IOException {
        return Files.readString(Path.of(fileName));
    }
    
    /**
     * Writes a list of strings to a file (each string becomes a line)
     * @param fileName Name of the file to write
     * @param lines List of strings to write
     * @throws IOException If an I/O error occurs
     */
    public static void writeLinesToFile(String fileName, List<String> lines) throws IOException {
        Files.write(Path.of(fileName), lines);
    }
}