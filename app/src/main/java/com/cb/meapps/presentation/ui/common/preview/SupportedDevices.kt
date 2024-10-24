package com.cb.meapps.presentation.ui.common.preview

import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Medium phone",
    device = "spec:id=reference_phone,shape=Normal,width=411,height=891,unit=dp,dpi=420"
)
@Preview(
    name = "foldable",
    device = "spec:id=reference_foldable,shape=Normal,width=673,height=841,unit=dp,dpi=420"
)
@Preview(
    name = "pixel_8_pro",
    device = "id:pixel_8_pro"
)
annotation class SupportedDevicesPreview