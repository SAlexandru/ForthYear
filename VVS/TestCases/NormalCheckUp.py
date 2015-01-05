#{{{ Marathon
from default import *
#}}} Marathon

def test():
    set_java_recorded_version("1.8.0_25")
    if window(''):
        assert_p('Start server', 'Text', 'Start server')
        assert_p('lbl:WebServer control', 'Text', 'WebServer control:')
        assert_p('JPanel_1', 'Enabled', 'true')
        assert_p('lbl:WebServer info', 'Text', 'WebServer info:')
        assert_p('WebServer control', 'Enabled', 'true')
        assert_p('lbl:Server listening on port', 'Text', 'Server listening on port:')
        click('lbl:Web root directory')
        click('lbl:Web root directory')
        click('lbl:Web root directory')
        assert_p('lbl:Web root directory', 'Text', 'Web root directory:')
        click('lbl:Maintenance directory', 'Meta')
        assert_p('lbl:Maintenance directory', 'Text', 'Maintenance directory:')
        assert_p('lbl:WebServer configuration', 'Text', 'WebServer configuration:')
        assert_p('lbl:WebServer info', 'Text', 'WebServer info:')
        click('lbl:Server status', 'Meta')
        assert_p('lbl:Server status', 'Text', 'Server status:')
        assert_p('lbl:Server address', 'Text', 'Server address:')
        assert_p('lbl:Server listening port', 'Text', 'Server listening port:')
        assert_p('lbl:Root Dir', 'Text', 'Root Dir:')
        assert_p('lbl:Maintenance Dir', 'Text', 'Maintenance Dir:')
        assert_p('lbl:Stopped', 'Text', 'Stopped')
        assert_p('Switch to maintenance mode', 'Text', 'false')
        assert_p('JPanel_3', 'Enabled', 'true')
        assert_p('SubmitConfig', 'Text', 'SubmitConfig')
        assert_p('SaveConfig', 'Text', 'SaveConfig')
    close()


    pass