E:
cd Scuola\SAMT_3\Progetti\NebulaWatches

set /p branch=Inserire nome nuova branch:

git checkout dev

git checkout -b %branch%

git push origin %branch%