cd ../
git submodule update --init
git submodule update --recursive
git submodule foreach git pull origin master
git pull
docker build -t mile-ocr-api .
