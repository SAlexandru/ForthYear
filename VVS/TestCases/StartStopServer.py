#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.8.0_25")
    if window(''):
        click('Start server')
        click('Stop server')
        rightclick('Start server')
        assert_p('Start server', 'Text', 'Start server')
        click('Start server')
        wait_p('Stop server', 'Text', 'Stop server')
        assert_p('Stop server', 'Text', 'Stop server')
        assert_p('Switch to maintenance mode', 'Text', 'false')
        select('Switch to maintenance mode', 'true')
        assert_p('Switch to maintenance mode', 'Text', 'true')
        select('Switch to maintenance mode', 'true')
    close()

    pass