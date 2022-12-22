package xyz.tigrou;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xyz.tigrou.tools.Tools;

public class DayG {

    public enum CommandType {
        CD,
        LS
    }

    // private static final String INPUT = "input_g_test.txt";
    private static final String INPUT = "input_g.txt";
     
    public static void main(String[] args) throws Exception {
        partOne();
        partTwo();
    }

    private static void partOne() throws Exception {
        List<String> lines = Tools.getInput(INPUT);

        List<Command> commands = new ArrayList<>();

        Command command = null;
        for (String line : lines) {
            // If line starts with $, it's a command
            if (line.startsWith("$")) {
                command = parseCommand(line);
                commands.add(command);
            }
            else {
                // It's a result
                CommandResult result = parseResult(line);
                command.addResult(result);
            }
        }

        Directory rootDirectory = buildFileSystem(commands);
        // System.out.println(rootDirectory.prettyPrint(0));

        List<Directory> directories = new ArrayList<>();
        findDirectoryBySize(rootDirectory, directories, 100000, true);
        int sumSize = 0;
        for (Directory directory : directories) {
            sumSize += directory.getSize();
        }
        System.out.println("Sum size: " + sumSize);
    }

    private static void partTwo() throws Exception {
        List<String> lines = Tools.getInput(INPUT);

        List<Command> commands = new ArrayList<>();

        Command command = null;
        for (String line : lines) {
            // If line starts with $, it's a command
            if (line.startsWith("$")) {
                command = parseCommand(line);
                commands.add(command);
            }
            else {
                // It's a result
                CommandResult result = parseResult(line);
                command.addResult(result);
            }
        }

        Directory rootDirectory = buildFileSystem(commands);
        System.out.println(rootDirectory.prettyPrint(0));

        int diskSize = 70000000;
        int diskNeeded = 30000000;
        int sizeAvailable = diskSize - rootDirectory.getSize();
        int minSizeToDelete = diskNeeded - sizeAvailable;


        List<Directory> directories = new ArrayList<>();
        findDirectoryBySize(rootDirectory, directories, minSizeToDelete, false);
        // Find the smallest directory which matches the size
        int minSize = Integer.MAX_VALUE;
        for (Directory directory : directories) {
            System.out.println("Found directory with size " + directory.getSize() + ": " + directory.getName() + "");
            if (directory.getSize() < minSize) {
                minSize = directory.getSize();
            }
        }
        System.out.println("Minimum size: " + minSize);
    }

    private static void findDirectoryBySize(Directory currentDirectory, List<Directory> directories, int size, boolean checkMaxSize) {
        if (checkMaxSize ? currentDirectory.getSize() <= size : currentDirectory.getSize() >= size) {
            directories.add(currentDirectory);
        }
        for (File file : currentDirectory.getFiles()) {
            if (file instanceof Directory) {
                findDirectoryBySize((Directory) file, directories, size, checkMaxSize);
            }
        }
    }

    private static Command parseCommand(String line) throws Exception {
        String[] parts = line.split(" ");
        if (parts[1].equals("ls")) {
            return new Command(CommandType.LS, null);
        }
        else if (parts[1].equals("cd")) {
            return new Command(CommandType.CD, parts[2]);
        }
        throw new Exception("Command not supported");
    }

    private static CommandResult parseResult(String line) {
        String[] parts = line.split(" ");
        if (parts[0].equals("dir")) {
            return new CommandResult(new Directory(parts[1]));
        }
        else {
            return new CommandResult(new File(parts[1], Integer.parseInt(parts[0])));
        }
    }

    private static Directory buildFileSystem(List<Command> commands) {
        Directory rootDirectory = null;
        Directory currentDirectory = null;

        for (Command command : commands) {
            // It is dependant of the previous command
            switch (command.getType()) {
            case CD:
                if (command.getArgs().equals("/")) {
                    if (rootDirectory == null) {
                        rootDirectory = new Directory("/");
                    }
                    currentDirectory = rootDirectory;
                }
                else if (command.getArgs().equals("..")) {
                    // We need to go back to the parent directory
                    if (currentDirectory != rootDirectory) {
                        currentDirectory = currentDirectory.getParent();
                    }
                }
                else {
                    // We need to go to the child directory
                    String directoryName = command.getArgs();
                    
                    boolean found = false;
                    for (File file : currentDirectory.getFiles()) {
                        if (file.getName().equals(directoryName)) {
                            currentDirectory = (Directory) file;
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        Directory newDirectory = new Directory(directoryName);
                        currentDirectory.addFile(newDirectory);
                        currentDirectory = newDirectory;
                    }
                }
                break;
            case LS:
                List<CommandResult> results = command.getResults();
                for (CommandResult result : results) {
                    currentDirectory.addFile(result.getFile());
                }
                break;
            default:
                break;
            }
        }

        return rootDirectory;
    }

    public static class Command {
            
            private CommandType type;
            private String args;
            private List<CommandResult> results;
    
            Command(CommandType type, String args) {
                this.type = type;
                this.args = args;
                this.results = new ArrayList<>();
            }
    
            CommandType getType() {
                return type;
            }
    
            String getArgs() {
                return args;
            }

            List<CommandResult> getResults() {
                return results;
            }

            void addResult(CommandResult result) {
                results.add(result);
            }
    }

    public static class CommandResult {
        
        private File file;

        CommandResult(File file) {
            this.file = file;
        }

        public File getFile() {
            return file;
        }
    }

    public static class File {

        private Directory parent;
        private String name;
        private int size;

        File(String name, int size) {
            this.name = name;
            this.size = size;
        }
        
        public Directory getParent() {
            return parent;
        }

        public void setParent(Directory parent) {
            this.parent = parent;
        }

        String getName() {
            return name;
        }

        int getSize() {
            return size;
        }

        public String prettyPrint(int indent) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < indent; i++) {
                sb.append(" ");
            }
            sb.append("- ").append(getName()).append(" (file, size=" + getSize() + ")\n");
            return sb.toString();
        }
    }

    public static class Directory extends File {

        private List<File> files;

        Directory(String name) {
            super(name, 0);
            files = new ArrayList<>();
        }

        void addFile(File file) {
            for (File f : files) {
                if (f.getName().equals(file.getName())) {
                    return;
                }
            }

            file.setParent(this);
            files.add(file);
        }

        List<File> getFiles() {
            return files;
        }

        @Override
        int getSize() {
            int size = 0;
            for (File file : files) {
                size += file.getSize();
            }
            return size;
        }

        public String prettyPrint(int indent) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < indent; i++) {
                sb.append(" ");
            }
            sb.append("- ").append(getName()).append(" (dir, size=" + (getSize() > 8381165 ? "*" : "") + getSize() + (getSize() > 8381165 ? "*" : "") + ")\n");
            for (File file : files) {
                sb.append(file.prettyPrint(indent + 2));
            }
            return sb.toString();
        }
    }
}
