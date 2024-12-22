/*
 [The "BSD licence"]
 Copyright (c) 2017 Ivan de Jesus Deras (ideras@gmail.com)
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package mc;

import purejavacomm.*;
import mc.MimasV2ConfigDownloader;

/**
 *
 * @author guigui220d
 */
public class TerminalProgress implements ProgrammingProgressListener {

    final int barLength = 25;
    
    @Override
    public void initProgress(int maxValue) {
      progressMax = maxValue;
      progress = 0;
      printProgress(false);
      //System.out.println("init progress at " + maxValue);
    }
    
    @Override
    public void updateProgress(int value) {
      progress = value;
      printProgress(false);
      //System.out.println("update progress at " + value);
    }

    private void printProgress(boolean newline) {
      if (progressMax == 0)
        return;

      if (progress > progressMax)
        progressMax = progress;
      
      int fill = progress * barLength / progressMax;
      System.out.print("[" + "=".repeat(fill) + " ".repeat(barLength - fill) + "] " + title + (newline ? "\n" : "\r"));
    }
    
    @Override
    public void logMessage(String message) {
      //printProgress(true);
      //System.out.println(message);
      // TODO: find way to intertwine logs and progress bar
    }
    
    @Override
    public void updateTitle(String message) {
      title = message;
      //printProgress(true);
      // System.out.println("Set title: " + message);
    }
    
    @Override
    public void errorMessage(String message) {
      printProgress(true);
      System.err.println("Error: " + message);
    }
    
    @Override
    public void programmingDone() {
      printProgress(true);
    }

    private int progress = 0;
    private int progressMax = 0;
    private String title = "";
}
