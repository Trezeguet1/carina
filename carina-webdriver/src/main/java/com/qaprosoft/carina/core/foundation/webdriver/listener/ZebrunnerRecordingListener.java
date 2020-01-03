/*******************************************************************************
 * Copyright 2013-2019 QaProSoft (http://www.qaprosoft.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.qaprosoft.carina.core.foundation.webdriver.listener;

import org.openqa.selenium.remote.Command;

import com.qaprosoft.zafira.models.dto.TestArtifactType;

/**
 * ZebrunnerRecordingListener - saves video artifact link before driver is closed.
 * 
 * @author akhursevich
 */
public class ZebrunnerRecordingListener implements IDriverCommandListener {

    private TestArtifactType videoArtifact;

    private boolean recording = false;

    public ZebrunnerRecordingListener(TestArtifactType videoArtifact) {
        this.videoArtifact = videoArtifact;
    }

    @Override
    public void beforeEvent(Command command) {
        if (recording) {
            registerArtifact(command, videoArtifact);
        }
    }

    @Override
    public void afterEvent(Command command) {
        if (!recording && command.getSessionId() != null) {
            videoArtifact.setLink(String.format(videoArtifact.getLink(), command.getSessionId().toString()));
            recording = true;
        }
    }

}