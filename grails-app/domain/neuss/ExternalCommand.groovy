package neuss

import java.util.List;


class ExternalCommand {
	
	static boolean useSSH = false

    static constraints = {
		command(blank:false)
		exitCode(nullable:true)
		stdout(nullable:true)
		stderr(nullable:false)
		executed(nullable:true)
		executed(nullable:false)
    }
	
	static hasMany = [stdout : String, stderr : String]
	
	String command
	int exitCode
	List<String> stdout
	List<String> stderr
	
	Date executed
	Date finished
	
	public ExternalCommand() {
	}
	
	public ExternalCommand(String command) {
		this.command = command
	}
	
	public String command() {
		if ( useSSH ) {
			return 'ssh gold@gold /opt/gold/bin/'+command
		} else {
			return command
		}
	}
	
	public boolean wasExecuted() {
		return ( executed != null )
	}
	
	public boolean wasSuccessful() {
		if ( wasExecuted() ) {
			if ( exitCode == 0 ) {
				return true;
			}
		}
		return false;
	}

	public List<String> getStdOut() {
		return stdout
	}

	public List<String> getStdErr() {
		return stderr
	}
	
	void execute() {
		
		if ( getExecuted() ) {
			throw new RuntimeException("Command already executed.")
		}
		
		setExecuted(new Date())
		def proc = command().execute()
		proc.waitFor()
		setFinished(new Date())
		
		setExitCode(proc.exitValue())
		def stdout = []
		proc.in.text.split('\n').each { it ->
			stdout.add(it.trim())
		}
		def stderr = []
		proc.err.text.split('\n').each { it ->
			stderr.add(it.trim())
		}
		setStdout(stdout)
		setStderr(stderr)
		
		save()

	}
	
	@Override
	public String toString() {
		return 'command: "'+command()+'", stdout: "'+stdout+'", stderr: "'+stderr+'", exitCode: "'+exitCode+'"'
	}
	
}
