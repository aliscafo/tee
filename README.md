# tee
Tee command is used to store and view (both at the same time) the output of any other command.
The behavior of the implemented version of tee is the same as in UNIX.

# build
```
./tee_build
```

**NOTE:** there will be a note concerning Sun proprietary API after building. SunAPI is used for handling signals in [-i] option, and using it in code is a cause of some warnings. It is because intercepting signals directly makes a Java program OS-dependent, so it's better to prevent such cases. To avoid warnings the flag  
-XDenableSunApiLintControl is used, and it prints the note about SunAPI.

# run 
```
java -jar tee.jar [-ai] [file ...]
```

**Options:**   
**-a** &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Append the output to the files rather than overwriting them.  
**-i** &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Ignore the SIGINT signal.
                   
           
