#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.8.0_25")
    if window(''):
        assert_p('lbl:Stopped', 'Text', 'Stopped')
        click('127.0.0.1')
        assert_p('lbl:Running', 'Text', 'Running')
    close()

    pass
