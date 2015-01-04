#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.8.0_25")
    if window(''):
        click('127.0.0.1')
        assert_p('lbl:10008', 'Text', '10008')
        assert_p('lbl:127.0.0.1', 'Text', '127.0.0.1')
        assert_p('lbl:Running', 'Text', 'Running')
        select('Switch to maintenance mode', 'true')
        select('Switch to maintenance mode', 'true')
        assert_p('lbl:10008', 'Text', '10008')
        assert_p('lbl:127.0.0.1', 'Text', '127.0.0.1')
        assert_p('lbl:Running', 'Text', 'Maintenance')
        select('Switch to maintenance mode', 'false')
        assert_p('lbl:127.0.0.1', 'Text', '127.0.0.1')
        select('Switch to maintenance mode', 'false')
        assert_p('lbl:10008', 'Text', '10008')
        assert_p('Running', 'Text', 'Running')
        click('Start server')
    close()

    pass
