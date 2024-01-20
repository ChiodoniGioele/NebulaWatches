E:
cd Scuola\SAMT_3\Progetti\NebulaWatches

set /p branch=Inserire nome branch da elliminare:

git checkout dev

git push origin --delete %branch%

git branch -d %branch%