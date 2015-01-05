#{{{ Marathon
from default import *
#}}} Marathon

def test():

    set_java_recorded_version("1.8.0_25")
    if window(''):
        rightclick('Start server')
        click('Start server', 'Meta')
        click('Stop server', 'Meta')
        click('Start server', 'Meta')
        assert_p('Stop server', 'Text', 'Stop server')
        assert_p('lbl:127.0.0.1', 'Text', '127.0.0.1')
        assert_p('lbl:8011', 'Text', '8011')
        select('Server listening on port', '10008')
        click('SubmitConfig')
        assert_p('lbl:10008', 'Text', '10008')
        click('Stop server')
        assert_p('Start server', 'Text', 'Start server')
        assert_p('lbl:10008', 'Text', '10008')
        select('Server listening on port', '8011')
        click('SubmitConfig')
        rightclick('lbl:8011')
        rightclick('lbl:8011')
        assert_p('lbl:8011', 'Text', '8011')
        assert_p('Start server', 'Text', 'Start server')
        click('Start server')
        assert_p('Stop server', 'Text', 'Stop server')
        select('Server listening on port', '10008')
        assert_p('lbl:8011', 'Text', '8011')
        click('SubmitConfig')
        assert_p('lbl:127.0.0.1', 'Text', '127.0.0.1')
        assert_p('Stop server', 'Text', 'Stop server')
        click('Stop server')
        assert_p('Start server', 'Text', 'Start server')
    close()

    pass