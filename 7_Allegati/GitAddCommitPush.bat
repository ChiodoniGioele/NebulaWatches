E:
cd Scuola\SAMT_3\Progetti\NebulaWatches

set /p branch=Inserire nome branch:

git checkout "%branch%"

git add *

set /p commento=Scrivi commento:

git commit -m "Commit at %time%  %date% - %commento%"

git push