# Capstone_FrontEnd

//ISBN Scanner를 가져오는 button 및 클릭 리스너
        ISBNCamera = (ImageButton) findViewById(R.id.ISBNCamera);
        ISBNCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });


    }

    private void scanCode() {
        //QC code 화면을 호출할 IntentIntegrator를 가져온다.
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        //default는 세로 이지만 휴대폰의 방향에 따라 가로, 세로로 자동 변경되게 하기 위해 false를 넣었다.
        integrator.setOrientationLocked(false);
        //스캔할 바코드의 포맷을 결정한다. 이때 모든 코드를 사용하겠다고 결정했다.
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        //스캔 화면과 같이 나오는 문자열
        integrator.setPrompt("Scanning code");
        //스캐너 시작하는 코드
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);
        }

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents() != null){       //스캔한 내용이 있으면 다음과 같이 Dialog를 출력해 준다.
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setMessage(result.getContents())
                        .setTitle("Scanning Result")
                        .setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                scanCode();
                            }
                        }).setNegativeButton("finish", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ISBNInput = (EditText)findViewById(R.id.ISBNInput);
                                ISBNInput.setText(result.getContents());
                                //finish();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            //스캔한 내용이 없을시 toast message 출력
            else{
                Toast.makeText(this,"No Results", Toast.LENGTH_LONG).show();
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
