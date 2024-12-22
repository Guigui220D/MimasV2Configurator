MIMAS V2 Configuration Tool
===========================

> [!NOTE]
> This is a fork, that is meant for terminal use of the configurator instead of GUI.

This is the source code for the [Mimas V2 FPGA Board](https://numato.com/mimas-v2-spartan-6-fpga-development-board-with-ddr-sdram/) configuration tool, in terminal mode.
This fork lets you use the application in a terminal instead of from the GUI.
The application is developed in Java and depends on the following libraries:

1. [Java Native Access (JNA)](https://github.com/java-native-access/jna)
2. [PureJavaComm](https://github.com/nyholku/purejavacomm)
2. [Apache Commons CLI](https://commons.apache.org/proper/commons-cli/)

# Build

```bash
./gradlew build
```

You can then find the binary in `./build/libs/mv2c.jar`.

# Usage

Start and get help with `java -jar mv2c.jar -h` (Java 17 recommended).

Example usage: `java -jar mv2c.jar -v -f -i bitstream.bin -o ttyACM0`.

## Options

- -i: bitstream binary (not a .bit) (mandatory)
- -o: serial comm port name (not the path, the name) (mandatory)
- -f: fast mode: use 115200 bauds
- -v: verify after flash
- -h: help
- -l: list serial com port names

License
--------

The software is released under the modified BSD License.

```
Redistribution and use in source and binary forms, with or without modification, are permitted provided 
that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and 
   the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions 
   and the following disclaimer in the documentation and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or 
   promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED 
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A 
PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR 
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, 
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN 
IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
```
