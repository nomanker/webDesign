public class CommandFactory {

    public static Command createCommand(String type) {

        type = type.toUpperCase();
        switch(type)
        {
            case "USER":return new UserCommand();

            case "PASS":return new PassCommand();

            case "LIST":return new DirCommand();

            case "MYPORT":return new PortCommand();

            case "QUIT":return new QuitCommand();

            case "RETR":return new RetrCommand();

            case "CWD":return new CwdCommand();

            case "STOR":return new StoreCommand();

            case "PASV":return new PasvCommand();

            case "DEL":return new DelCommand();

            case "RENAME":return new RenameCommand();

            case "MKD": return new MkdCommand();
            default :return null;
        }

    }
}