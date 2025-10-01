#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass any JVM options to Gradle separately.
DEFAULT_JVM_OPTS=""

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

# OS specific support.
cygwin=false
darwin=false
mingw=false
case "`uname`" in
  CYGWIN*)
    cygwin=true
    ;;
  Darwin*)
    darwin=true
    ;;
  MINGW*)
    mingw=true
    ;;
esac

# For Cygwin, ensure paths are in UNIX format before anything is touched.
if ${cygwin} ; then
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
fi

# Attempt to set APP_HOME
# Resolve links: $0 may be a link
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`"/$link"
  fi
done
SAVED="`pwd`"
cd "`dirname \"$PRG\"`" >/dev/null
APP_HOME="`pwd -P`"
cd "$SAVED" >/dev/null

# Attempt to set JAVA_HOME if it is not set
if [ -z "$JAVA_HOME" ]; then
    # If we are running in WSL try to find a java not in /mnt
    if [ -n "$WSL_DISTRO_NAME" ]; then
        for JAVA_CANDIDATE in \
            "/usr/lib/jvm/java-11-openjdk-amd64" \
            "/usr/lib/jvm/java-11-openjdk" \
            "/usr/lib/jvm/java-11-oracle" \
            "/usr/lib/jvm/java-8-openjdk-amd64" \
            "/usr/lib/jvm/java-8-openjdk" \
            "/usr/lib/jvm/java-8-oracle" \
            ; do
            if [ -d "$JAVA_CANDIDATE" ]; then
                export JAVA_HOME="$JAVA_CANDIDATE"
                break
            fi
        done
    fi
    # If it's still not set, search for a JDK
    if [ -z "$JAVA_HOME" ]; then
        if [ -x "/usr/libexec/java_home" ]; then
            export JAVA_HOME=`/usr/libexec/java_home`
        else
            # We are probably on Linux.
            # Try to find a JDK in a known location.
            # The code is based on the spring-boot-cli script.
            for JAVA_CANDIDATE in \
                "/usr/lib/jvm/java-17-openjdk-amd64" \
                "/usr/lib/jvm/java-17-openjdk" \
                "/usr/lib/jvm/java-17-oracle" \
                "/usr/lib/jvm/java-11-openjdk-amd64" \
                "/usr/lib/jvm/java-11-openjdk" \
                "/usr/lib/jvm/java-11-oracle" \
                "/usr/lib/jvm/java-8-openjdk-amd64" \
                "/usr/lib/jvm/java-8-openjdk" \
                "/usr/lib/jvm/java-8-oracle" \
                ; do
                if [ -d "$JAVA_CANDIDATE" ]; then
                    export JAVA_HOME="$JAVA_CANDIDATE"
                    break
                fi
            done
        fi
    fi
fi

# Check for a valid Java executable
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Increase the maximum number of open files
if [ "$MAX_FD" != "0" ] ; then
    # In POSIX sh, ulimit is a built-in, so it is not an executable
    # that can be located with `command -v`.
    # Let's check if the built-in is available.
    if ulimit -n >/dev/null 2>&1 ; then
        MAX_FD_LIMIT=`ulimit -H -n`
        if [ "$MAX_FD" = "maximum" -o "$MAX_FD" = "max" ] ; then
            # Use the hard limit
            MAX_FD="$MAX_FD_LIMIT"
        fi
        if [ "$MAX_FD" -gt "$MAX_FD_LIMIT" ]; then
            echo "Value of MAX_FD is too large ($MAX_FD), using limit ($MAX_FD_LIMIT) instead."
            MAX_FD=$MAX_FD_LIMIT
        fi
        ulimit -n $MAX_FD
        if [ $? -ne 0 ]; then
            echo "Could not set maximum file descriptor limit: $MAX_FD"
        fi
    else
        echo "Could not find ulimit to set maximum file descriptor limit"
    fi
fi

# For Cygwin, switch paths to Windows format before running java
if ${cygwin} ; then
  APP_HOME=`cygpath --path --windows "$APP_HOME"`
  JAVA_HOME=`cygpath --path --windows "$JAVA_HOME"`
  CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
fi

# For MINGW, switch paths to Windows format before running java
if ${mingw} ; then
  APP_HOME=`(cd "$APP_HOME" && pwd -W)`
  JAVA_HOME=`(cd "$JAVA_HOME" && pwd -W)`
  # That means that we are running in Git BASH
  CLASSPATH=`(cd "$CLASSPATH" && pwd -W)`
fi

# Split up the JVM options string into an array, following the shell quoting and substitution rules
function jvm_options_to_array {
    local jvm_options_string="$1"
    local jvm_options_array=()
    local arg
    eval "set -- $jvm_options_string"
    for arg do
        jvm_options_array+=("$arg")
    done
    echo "${jvm_options_array[@]}"
}

# Collect all arguments for the java command, following the shell quoting and substitution rules
function collect_all_args {
    local all_args_string="$1"
    local all_args_array=()
    local arg
    eval "set -- $all_args_string"
    for arg do
        all_args_array+=("$arg")
    done
    echo "${all_args_array[@]}"
}

# Escape application args
save () {
    for i do
        printf %s\\n "$i" | sed "s/'/'\\\\''/g;1s/^/'/;\$s/\$/'/"
    done
    echo " "
}
APP_ARGS_AS_STRING=$(save "$@")

exec "$JAVACMD" \
  `jvm_options_to_array "${DEFAULT_JVM_OPTS} ${JAVA_OPTS} ${GRADLE_OPTS}"` \
  -Dorg.gradle.appname="$APP_BASE_NAME" \
  -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" \
  org.gradle.wrapper.GradleWrapperMain \
  `collect_all_args "$APP_ARGS_AS_STRING"`